package nl.laurs.persistance;

import nl.laurs.domain.Release;

/**
 * @author: ML
 */
public interface ReleaseDao extends GenericEntityDao<Release> {
    /**
     * @param discogsId
     * @return null if there isn't a release with this id
     */
    Release getByDiscogsId(Integer discogsId);
}
