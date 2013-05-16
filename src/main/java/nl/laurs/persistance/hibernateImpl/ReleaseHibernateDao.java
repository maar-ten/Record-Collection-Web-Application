package nl.laurs.persistance.hibernateImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.domain.Release;
import nl.laurs.persistance.ReleaseDao;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author: Maarten
 */
@Repository
@Transactional(readOnly = true)
public class ReleaseHibernateDao extends AbstractGenericHibernateDao<Release> implements ReleaseDao {
    protected ReleaseHibernateDao() {
        super(Release.class);
    }

    public Release getByDiscogsId(Integer discogsId) {
        return (Release) createCriteria().add(eq(Release.DISCOGS_ID, discogsId)).uniqueResult();
    }
}
