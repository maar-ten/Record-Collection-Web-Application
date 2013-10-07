package nl.laurs.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.wicket.util.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.discogs.domain.DiscogsReleaseImage;
import nl.laurs.discogs.domain.DiscogsReleaseLabel;
import nl.laurs.domain.Artist;
import nl.laurs.domain.Label;
import nl.laurs.domain.Release;
import nl.laurs.domain.Track;
import nl.laurs.service.ArtistService;
import nl.laurs.service.DiscogsApiService;
import nl.laurs.service.LabelService;
import nl.laurs.service.impl.DiscogsApiServiceImpl.DiscogsApi.GET;
import nl.laurs.service.impl.DiscogsApiServiceImpl.DiscogsApi.IMAGE;
import nl.laurs.service.impl.DiscogsApiServiceImpl.DiscogsApi.LABEL;
import nl.laurs.service.impl.DiscogsApiServiceImpl.DiscogsApi.RELEASE;

/**
 * Service for getting data from Discogs, and converting it
 *
 * @author: Maarten
 */
@Service
@Transactional(readOnly = true)
public class DiscogsApiServiceImpl implements DiscogsApiService {
    public static final Logger LOG = Logger.getLogger(DiscogsApiServiceImpl.class);

    @Autowired
    private ArtistService artistService;
    @Autowired
    private LabelService labelService;

    private static final String USER_AGENT_STRING = "DiscographyBuilderService/1.0 +http://laurs.nl";

    @SuppressWarnings("unchecked")
    public Map<String, Object> getReleaseDataMap(Integer discogsReleaseId) {
        try {
            URL apiUrl = new URL(GET.BASE_URL + GET.RELEASES + discogsReleaseId.toString());
            //            File apiUrl = new File(
            //                    "D:\\intelliJ_workspace\\wicket_spring_hibernate_application\\src\\main\\webapp\\js\\discogsReleaseResponse.json");

            return new ObjectMapper().readValue(apiUrl, Map.class);
        }
        catch (Exception e) {
            LOG.error("Getting the data failed: ", e);
        }
        return null;
    }

    private HttpURLConnection getDiscogsHttpURLConnection(URL discogsApiUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) discogsApiUrl.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT_STRING);
        connection.connect();
        if (connection.getResponseCode() != 200) {
            LOG.warn(appendHeaderFields(connection.getHeaderFields()));
            throw new RuntimeException("The Discogs server request was not OK (" + connection.getResponseCode() + ")");
        }
        return connection;
    }

    private String appendHeaderFields(Map<String, List<String>> headerFields) {
        StringBuilder b = new StringBuilder();
        for (Entry<String, List<String>> entry : headerFields.entrySet()) {
            b.append(entry.getKey()).append(": ").append(entry.getValue());
        }
        return b.toString();
    }

    public Release createRelease(Map<String, Object> releaseData) {
        Release release = new Release();
        release.setDiscogsId((Integer) releaseData.get(DiscogsApi.RELEASE.ID));
        release.setTitle((String) releaseData.get(DiscogsApi.RELEASE.TITLE));
        release.setYear((Integer) releaseData.get(DiscogsApi.RELEASE.YEAR));

        for (Artist artist : getArtists(DiscogsApi.RELEASE.ARTISTS, releaseData)) {
            release.addArtist(artist);
        }

        for (Track track : getTracks(RELEASE.TRACKLIST, releaseData)) {
            release.addTrack(track);
            track.setRelease(release);
        }

        return release;
    }

    public Release createRelease(Map<String, Object> releaseData, DiscogsReleaseLabel releaseLabel,
            DiscogsReleaseImage releaseImage) {
        Release release = createRelease(releaseData);
        release.setLabel(getOrCreateLabel(releaseLabel));

        if (releaseImage != null) {
            loadDiscogsImageData(releaseImage);
            release.setImage(releaseImage.getImageData());
            release.setImageType(releaseImage.getImageType());
        }

        return release;
    }

    private void loadDiscogsImageData(DiscogsReleaseImage releaseImage) {
        InputStream in = null;
        try {
            URL apiUri = new URL(releaseImage.getUri());
            HttpURLConnection connection = getDiscogsHttpURLConnection(apiUri);
            in = connection.getInputStream();
            releaseImage.setImageData(IOUtils.toByteArray(in));
            releaseImage.setImageType(connection.getContentType());
        }

        catch (Exception e) {
            LOG.error("Getting the image failed: ", e);
            throw new RuntimeException("Getting the image failed", e);
        }

        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (Exception e) {
                    LOG.error("Closing the input stream failed: ", e);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<DiscogsReleaseImage> getReleaseImages(Map<String, Object> releaseDataMap) {
        List<DiscogsReleaseImage> releaseImages = new ArrayList<>();
        if (releaseDataMap.containsKey(RELEASE.IMAGES)) {
            List<Map<String, Object>> images = (List<Map<String, Object>>) releaseDataMap.get(
                    DiscogsApi.RELEASE.IMAGES);
            for (Map<String, Object> image : images) {
                releaseImages.add(
                        new DiscogsReleaseImage((String) image.get(IMAGE.URI), (String) image.get(IMAGE.URI150)));
            }
        }
        return releaseImages;
    }

    @SuppressWarnings("unchecked")
    public List<DiscogsReleaseLabel> getDiscogsReleaseLabels(Map<String, Object> releaseDataMap) {
        List<DiscogsReleaseLabel> discogsLabels = new ArrayList<>();
        if (releaseDataMap.containsKey(RELEASE.LABELS)) {
            List<Map<String, Object>> labels = (List<Map<String, Object>>) releaseDataMap.get(
                    DiscogsApi.RELEASE.LABELS);
            for (Map<String, Object> label : labels) {
                DiscogsReleaseLabel releaseLabel = new DiscogsReleaseLabel((String) label.get(LABEL.NAME),
                        (Integer) label.get(LABEL.ID), (String) label.get(LABEL.CATNO));
                discogsLabels.add(releaseLabel);
            }
        }
        return discogsLabels;
    }

    @SuppressWarnings("unchecked")
    private List<Artist> getArtists(String key, Map releaseData) {
        List<Artist> artists = new ArrayList<>();
        List<Map<String, Object>> artistMaps = (List<Map<String, Object>>) releaseData.get(key);
        for (Map<String, Object> artistMap : artistMaps) {
            artists.add(getOrCreateArtist(artistMap));
        }
        return artists;
    }

    @SuppressWarnings("unchecked")
    private List<Track> getTracks(String key, Map<String, Object> releaseData) {
        ArrayList<Track> tracks = new ArrayList<>();
        List<Map<String, Object>> trackMaps = (List<Map<String, Object>>) releaseData.get(key);
        for (Map<String, Object> trackMap : trackMaps) {
            tracks.add(getTrack(trackMap));
        }
        return tracks;
    }

    private Track getTrack(Map<String, Object> trackMap) {
        Track track = new Track();
        track.setTitle((String) trackMap.get(DiscogsApi.TRACK.TITLE));
        track.setPosition((String) trackMap.get(DiscogsApi.TRACK.POSITION));
        return track;
    }

    private Label getOrCreateLabel(DiscogsReleaseLabel releaseLabel) {
        Label label = labelService.getByDiscogsId(releaseLabel.getDiscogsId());
        if (label == null) {
            label = new Label();
            label.setDiscogsId(releaseLabel.getDiscogsId());
            label.setName(releaseLabel.getName());
        }
        return label;
    }

    private Artist getOrCreateArtist(Map<String, Object> artistMap) {
        Artist artist = artistService.getByDiscogsId((Integer) artistMap.get(DiscogsApi.ARTIST.ID));
        if (artist == null) {
            artist = new Artist();
            artist.setName((String) artistMap.get(DiscogsApi.ARTIST.NAME));
            artist.setDiscogsId((Integer) artistMap.get(DiscogsApi.ARTIST.ID));
        }
        return artist;
    }

    /**
     * Contains the Discogs api information; json keys, date format, et cetera
     */
    public static class DiscogsApi {

        public static final class GET {
            public static final String BASE_URL = "http://api.discogs.com/";
            public static final String RELEASES = "releases/";
        }

        public static final class RELEASE {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String ARTISTS = "artists";
            public static final String YEAR = "year";
            public static final String LABELS = "labels";
            public static final String IMAGES = "images";
            public static final String TRACKLIST = "tracklist";
        }

        public static final class ARTIST {
            public static final String ID = "id";
            public static final String NAME = "name";
        }

        public static final class LABEL {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String CATNO = "catno";
        }

        public static final class IMAGE {
            public static final String URI = "uri";
            public static final String URI150 = "uri150";
        }

        public static final class TRACK {
            public static final String DURATION = "duration";
            public static final String POSITION = "position";
            public static final String TITLE = "title";
        }
    }
}
