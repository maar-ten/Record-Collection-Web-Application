package nl.laurs.domain.Mothers;

import nl.laurs.domain.Track;

/**
 * Creates track objects for testing.
 *
 * @author: Maarten
 * @see <a href="http://martinfowler.com/bliki/ObjectMother.html">ObjectMother by Fowler</a>
 */
public class TrackMother {

    private static int nextId = 1;

    public static Track createExisting() {
        Track track = new Track();
        track.setTitle("trackTitle" + nextId);
        track.setId(nextId++);
        return track;
    }
}
