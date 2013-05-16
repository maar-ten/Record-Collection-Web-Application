package nl.laurs.persistance.hibernateImpl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.domain.AbstractGenericEntity;
import nl.laurs.persistance.GenericEntityDao;

/**
 * @author: Maarten
 */
@Repository
@Transactional(readOnly = true)
public abstract class AbstractGenericHibernateDao<T extends AbstractGenericEntity> implements GenericEntityDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> entityClass;

    protected AbstractGenericHibernateDao(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Criteria createCriteria() {
        return getCurrentSession().createCriteria(entityClass);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings({ "unchecked" })
    public Collection<T> getAll() {
        return createCriteria().list();
    }

    @SuppressWarnings({ "unchecked" })
    public T get(Integer id) {
        return (T) getCurrentSession().get(entityClass, id);
    }

    @Transactional(readOnly = false)
    public void save(T item) {
        getCurrentSession().saveOrUpdate(item);
    }

    @Transactional(readOnly = false)
    public void delete(T item) {
        getCurrentSession().delete(item);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<T> getSome(long offset, long size, String orderProperty) {
        if (offset > Integer.MAX_VALUE || offset < Integer.MIN_VALUE || size > Integer.MAX_VALUE
            || size < Integer.MIN_VALUE) {
            throw new IllegalStateException("Parameters are out of the subList method's bounds.");
        }

        Criteria criteria = createCriteria().setFirstResult((int) offset).setFetchSize((int) size);
        criteria.addOrder(Order.asc(orderProperty));
        return criteria.list();
    }

    @Override
    public int countAll() {
        return (int) createCriteria().setProjection(Projections.count(AbstractGenericEntity.ID)).uniqueResult();
    }
}
