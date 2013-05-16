package nl.laurs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.persistance.ReleaseDao;
import nl.laurs.domain.Release;
import nl.laurs.service.ReleaseService;

/**
 * @author: Maarten
 */
@Service
@Transactional(readOnly = true)
public class ReleaseServiceImpl extends AbstractGenericEntityServiceImpl<Release> implements ReleaseService {

    @Autowired
    public void setEntityDao(ReleaseDao releaseDao) {
        super.setEntityDao(releaseDao);
    }

    private ReleaseDao getReleaseDao() {
        return (ReleaseDao) entityDao;
    }
    public Release getByDiscogsId(Integer discogsId) {
        return getReleaseDao().getByDiscogsId(discogsId);
    }
}
