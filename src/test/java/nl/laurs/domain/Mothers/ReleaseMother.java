package nl.laurs.domain.Mothers;

import nl.laurs.domain.Release;

/**
 * Creates release objects for testing.
 *
 * @author: Maarten
 * @see <a href="http://martinfowler.com/bliki/ObjectMother.html">ObjectMother by Fowler</a>
 */
public class ReleaseMother {
    private static int nextId = 1;
    private static int nextDiscogsId = 1;

    public static Release createExisting() {
        Release release = new Release();
        release.setTitle("releaseTitle" + nextId);
        release.setId(nextId++);
        release.setDiscogsId(nextDiscogsId++);
        return release;
    }
}
