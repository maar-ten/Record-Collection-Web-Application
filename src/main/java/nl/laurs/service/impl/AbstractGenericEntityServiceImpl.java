package nl.laurs.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.domain.AbstractGenericEntity;
import nl.laurs.persistance.GenericEntityDao;
import nl.laurs.service.GenericEntityService;

/**
 * @author: ML
 */
@Service
@Transactional(readOnly = true)
public abstract class AbstractGenericEntityServiceImpl<T extends AbstractGenericEntity> implements GenericEntityService<T> {

    protected GenericEntityDao<T> entityDao;

    public void setEntityDao(GenericEntityDao<T> entityDao) {
        this.entityDao = entityDao;
    }

    public Collection<T> getAll() {
        return entityDao.getAll();
    }

    public T get(Integer id) {
        return entityDao.get(id);
    }

    @Transactional(readOnly = false)
    public void delete(T entity) {
        entityDao.delete(entity);
    }

    @Override
    public Collection<T> getSome(long offset, long size, String orderProperty) {
        return entityDao.getSome(offset, size, orderProperty);
    }

    @Override
    public int countAll() {
        return entityDao.countAll();
    }

    @Transactional(readOnly = false)
    public void save(T entity) {
        entityDao.save(entity);
    }
}
