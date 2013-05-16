package nl.laurs.view.model;

import org.apache.wicket.spring.injection.annot.SpringBean;

import nl.laurs.domain.Track;
import nl.laurs.service.GenericEntityService;
import nl.laurs.service.TrackService;

/**
 * @author: ML
 */
public class TrackModel extends AbstractEntityModel<Track> {
    @SpringBean
    private TrackService trackService;

    public TrackModel(Track track) {
        super(track);
    }

    @Override
    protected Track create() {
        return new Track();
    }

    @Override
    protected GenericEntityService<Track> getEntityService() {
        return trackService;
    }

    private Track getTrack() {
        return getObject();
    }
}
