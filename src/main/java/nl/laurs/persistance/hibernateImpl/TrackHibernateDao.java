package nl.laurs.persistance.hibernateImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.persistance.TrackDao;
import nl.laurs.domain.Track;

/**
 * @author: ML
 */
@Repository
@Transactional(readOnly = true)
public class TrackHibernateDao extends AbstractGenericHibernateDao<Track> implements TrackDao {
    protected TrackHibernateDao() {
        super(Track.class);
    }
}
