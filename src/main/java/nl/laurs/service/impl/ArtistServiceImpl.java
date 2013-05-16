package nl.laurs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.laurs.domain.Artist;
import nl.laurs.persistance.ArtistDao;
import nl.laurs.service.ArtistService;

/**
 * @author: ML
 */
@Service
@Transactional(readOnly = true)
public class ArtistServiceImpl extends AbstractGenericEntityServiceImpl<Artist> implements ArtistService {

    @Autowired
    public ArtistServiceImpl(ArtistDao artistDao) {
        super.setEntityDao(artistDao);
    }

    private ArtistDao getArtistDao() {
        return (ArtistDao) entityDao;
    }

    public List<Artist> findByName(String name) {
        return getArtistDao().findByName(name);
    }

    public Artist getByDiscogsId(Integer discogsId) {
        return getArtistDao().getByDiscogsId(discogsId);
    }
}
