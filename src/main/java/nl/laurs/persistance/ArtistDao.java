package nl.laurs.persistance;

import java.util.List;

import nl.laurs.domain.Artist;

/**
 * @author: ML
 */
public interface ArtistDao extends GenericEntityDao<Artist> {
    /**
     * @param name
     * @return a list of artists with a name like the name given
     */
    List<Artist> findByName(String name);

    /**
     * @param discogsId
     * @return null if there isn't an artist with this id
     */
    Artist getByDiscogsId(Integer discogsId);
}
