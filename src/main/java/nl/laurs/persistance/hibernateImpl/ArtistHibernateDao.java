package nl.laurs.persistance.hibernateImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.persistance.ArtistDao;
import nl.laurs.domain.Artist;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.like;
import static org.hibernate.criterion.Restrictions.or;

/**
 * @author: ML
 */
@Repository
@Transactional(readOnly = true)
public class ArtistHibernateDao extends AbstractGenericHibernateDao<Artist> implements ArtistDao {
    protected ArtistHibernateDao() {
        super(Artist.class);
    }

    @SuppressWarnings("unchecked")
    public List<Artist> findByName(String name) {
        String likeName = "%" + name + "%";
        Criteria criteria = createCriteria();
        criteria.add(like(Artist.NAME, likeName));
        return criteria.list();
    }

    public Artist getByDiscogsId(Integer discogsId) {
        Criteria criteria = createCriteria();
        criteria.add(eq(Artist.DISCOGS_ID, discogsId));
        return (Artist) criteria.uniqueResult();
    }
}
