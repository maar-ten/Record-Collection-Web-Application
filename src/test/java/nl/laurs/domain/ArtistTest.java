package nl.laurs.domain;

import org.junit.Before;
import org.junit.Test;

import nl.laurs.domain.Mothers.ReleaseMother;

import static org.junit.Assert.assertEquals;

/**
 * @author: Maarten
 */
public class ArtistTest {
    private Artist artist;
    private Release release;

    @Before
    public void setUp() throws Exception {
        int id = 4;
        int discogsId = 2;
        String name = "someName";
        release = ReleaseMother.createExisting();

        artist = new Artist();
        artist.setName(name);
        artist.setId(id);
        artist.setDiscogsId(discogsId);
        artist.addRelease(release);
    }

    @Test
    public void constructArtist() throws Exception {
        assertEquals(4, artist.getId().intValue());
        assertEquals(2, artist.getDiscogsId().intValue());
        assertEquals("someName", artist.getName());
        assertEquals("someName", artist.toString());

        assertEquals(1, artist.getReleases().size());
        assertEquals(release, artist.getReleases().iterator().next());
    }

    @Test
    public void removeRelease() throws Exception {
        artist.removeRelease(release);
        assertEquals(0, artist.getReleases().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSet() throws Exception {
        artist.getReleases().remove(release);
    }
}
