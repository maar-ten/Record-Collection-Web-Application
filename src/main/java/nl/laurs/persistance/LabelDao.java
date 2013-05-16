package nl.laurs.persistance;

import nl.laurs.domain.Label;

/**
 * @author: ML
 */
public interface LabelDao extends GenericEntityDao<Label> {
    /**
     * @param discogsId
     * @return null if there isn't a label with this id
     */
    Label getByDiscogsId(Integer discogsId);
}
