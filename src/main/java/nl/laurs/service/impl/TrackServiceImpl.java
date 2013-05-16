package nl.laurs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.persistance.TrackDao;
import nl.laurs.domain.Track;
import nl.laurs.service.TrackService;

/**
 * @author: ML
 */
@Service
@Transactional(readOnly = true)
public class TrackServiceImpl extends AbstractGenericEntityServiceImpl<Track> implements TrackService {

    @Autowired
    public TrackServiceImpl(TrackDao trackDao) {
        super.setEntityDao(trackDao);
    }
}
