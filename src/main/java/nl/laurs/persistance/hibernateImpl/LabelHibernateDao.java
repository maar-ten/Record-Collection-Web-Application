package nl.laurs.persistance.hibernateImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.domain.Label;
import nl.laurs.persistance.LabelDao;

import static org.hibernate.criterion.Restrictions.eq;

/**
 * @author: ML
 */
@Repository
@Transactional(readOnly = true)
public class LabelHibernateDao extends AbstractGenericHibernateDao<Label> implements LabelDao {
    protected LabelHibernateDao() {
        super(Label.class);
    }

    public Label getByDiscogsId(Integer discogsId) {
        return (Label) createCriteria().add(eq(Label.DISCOGS_ID, discogsId)).uniqueResult();
    }
}
