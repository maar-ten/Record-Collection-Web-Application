package nl.laurs.domain;

import org.junit.Before;
import org.junit.Test;

import nl.laurs.domain.Mothers.ArtistMother;
import nl.laurs.domain.Mothers.LabelMother;
import nl.laurs.domain.Mothers.TrackMother;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author: Maarten
 */
public class ReleaseTest {

    private Release release;
    private Label label;
    private byte[] bytes;
    private Artist artist;
    private Track track;

    @Before
    public void setUp() throws Exception {
        int id = 8;
        int discogsId = 3;
        int year = 2010;
        String title = "releaseTitle";
        String catno = "Foobar29";
        String imageType = "png";

        label = LabelMother.createExisting();
        bytes = new byte[] { 10, 20, 30 };
        artist = ArtistMother.createExisting();
        track = TrackMother.createExisting();

        release = new Release();
        release.setId(id);
        release.setDiscogsId(discogsId);
        release.setTitle(title);
        release.setCatno(catno);
        release.setYear(year);
        release.setLabel(label);
        release.setImage(bytes);
        release.setImageType(imageType);
        release.setVideoUri(null);
        release.addArtist(artist);
        release.addTrack(track);
    }

    @Test
    public void constructRelease() throws Exception {
        assertEquals(8, release.getId().intValue());
        assertEquals(3, release.getDiscogsId().intValue());
        assertEquals(2010, release.getYear().intValue());
        assertEquals("releaseTitle", release.getTitle());
        assertEquals("Foobar29", release.getCatno());
        assertEquals(label, release.getLabel());
        assertEquals(bytes, release.getImage());
        assertEquals("png", release.getImageType());
        assertNull(release.getVideoUri());
        assertEquals(1, release.getArtists().size());
        assertEquals(artist, release.getArtists().iterator().next());
        assertEquals(1, release.getTracks().size());
        assertEquals(track, release.getTracks().iterator().next());
    }

    @Test
    public void removingArtist() throws Exception {
        release.removeArtist(artist);
    }

    @Test
    public void removingTrack() throws Exception {
        release.removeTrack(track);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSetArtist() throws Exception {
        release.getArtists().remove(artist);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSetTrack() throws Exception {
        release.getTracks().remove(track);
    }
}
