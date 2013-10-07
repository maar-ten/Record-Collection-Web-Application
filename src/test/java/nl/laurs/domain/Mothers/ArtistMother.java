package nl.laurs.domain.Mothers;

import nl.laurs.domain.Artist;

/**
 * Creates artist objects for testing.
 *
 * @author: Maarten
 * @see <a href="http://martinfowler.com/bliki/ObjectMother.html">ObjectMother by Fowler</a>
 */
public class ArtistMother {
    private static int nextId = 1;
    private static int nextDiscogsId = 1;

    public static Artist createExisting() {
        Artist artist = new Artist();
        artist.setName("someArtistName" + nextId);
        artist.setId(nextId++);
        artist.setDiscogsId(nextDiscogsId++);
        return artist;
    }
}
