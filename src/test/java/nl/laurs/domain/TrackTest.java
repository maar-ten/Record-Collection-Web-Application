package nl.laurs.domain;

import org.junit.Before;
import org.junit.Test;

import nl.laurs.domain.Mothers.ReleaseMother;
import nl.laurs.domain.Mothers.TrackMother;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Maarten
 */
public class TrackTest {

    private Track track;
    private Release release;

    @Before
    public void setUp() throws Exception {
        int id = 9;
        String title = "someTitle";
        String position = "A1";
        release = ReleaseMother.createExisting();

        track = new Track();
        track.setPosition(position);
        track.setRelease(release);
        track.setTitle(title);
        track.setId(id);
    }

    @Test
    public void constructTrack() throws Exception {
        assertEquals(9, track.getId().intValue());
        assertEquals("someTitle", track.getTitle());
        assertEquals(release, track.getRelease());
        assertEquals("A1", track.getPosition());
    }

    @Test
    public void comparingTrackPositions() throws Exception {
        Track track2 = TrackMother.createExisting();
        track2.setPosition("A2");

        assertTrue(track.compareTo(track2) < 0);
    }

    @Test
    public void comparingTrackTitles() throws Exception {
        track.setPosition(null);
        track.setTitle("A");
        Track track2 = TrackMother.createExisting();
        track2.setPosition(null);
        track2.setTitle("B");

        assertTrue(track.compareTo(track2) < 0);
    }
}
