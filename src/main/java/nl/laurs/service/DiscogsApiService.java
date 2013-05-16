package nl.laurs.service;

import java.util.List;
import java.util.Map;

import nl.laurs.discogs.domain.DiscogsReleaseImage;
import nl.laurs.discogs.domain.DiscogsReleaseLabel;
import nl.laurs.domain.Release;

/**
 * @author: ML
 */
public interface DiscogsApiService {

    /**
     * Should query the Discogs api with the given release ID, and return the data as a Java map
     *
     * @param discogsReleaseId
     * @return may be null
     */
    Map<String, Object> getReleaseDataMap(Integer discogsReleaseId);

    /**
     * @param releaseDataMap
     * @return may be null
     */
    Release createRelease(Map<String, Object> releaseDataMap);

    /**
     * @param releaseData
     * @param releaseLabel
     * @param releaseImage
     * @return may be null
     */
    Release createRelease(Map<String, Object> releaseData, DiscogsReleaseLabel releaseLabel,
            DiscogsReleaseImage releaseImage);

    /**
     * Returns the list of {@link nl.laurs.discogs.domain.DiscogsReleaseImage}s that the releaseDataMap contains
     *
     * @param releaseDataMap
     * @return an empty list if there aren't any images
     */
    List<DiscogsReleaseImage> getReleaseImages(Map<String, Object> releaseDataMap);

    /**
     * Returns the list of {@link DiscogsReleaseLabel}s that the releaseDataMap contains
     *
     * @param releaseDataMap
     * @return list should not be empty
     */
    List<DiscogsReleaseLabel> getDiscogsReleaseLabels(Map<String, Object> releaseDataMap);
}
