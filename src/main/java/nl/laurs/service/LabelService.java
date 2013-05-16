package nl.laurs.service;

import nl.laurs.domain.Label;

/**
 * @author: ML
 */
public interface LabelService extends GenericEntityService<Label> {
    /**
     * @param discogsId
     * @return null if there isn't a label with this id
     */
    Label getByDiscogsId(Integer discogsId);
}
