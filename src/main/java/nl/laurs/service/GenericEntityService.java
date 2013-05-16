package nl.laurs.service;

import java.util.Collection;

import nl.laurs.domain.AbstractGenericEntity;

/**
 * @author: ML
 */
public interface GenericEntityService<T extends AbstractGenericEntity> {

    T get(Integer id);

    /**
     * @return a collection of all {@link T}
     */
    Collection<T> getAll();

    /**
     * May lead to an update instead of a save when the item has already been persisted once
     *
     * @param item
     */
    void save(T item);

    void delete(T item);

    /**
     * Gets a ordered collection of {@link T} of a certain size and starting point.
     *
     * @param offset
     * @param size
     * @param orderProperty
     * @return never null
     */
    Collection<T> getSome(long offset, long size, String orderProperty);

    /**
     * @return the number of entities in the database
     */
    int countAll();
}
