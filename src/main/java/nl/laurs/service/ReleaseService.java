package nl.laurs.service;

import nl.laurs.domain.Release;

/**
 * @author: Maarten
 */
public interface ReleaseService extends GenericEntityService<Release> {
    /**
     * @param discogsId
     * @return null if there isn't a release with this id
     */
    Release getByDiscogsId(Integer discogsId);
}
