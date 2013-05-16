package nl.laurs.view.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import nl.laurs.domain.Release;
import nl.laurs.service.GenericEntityService;
import nl.laurs.service.ReleaseService;

/**
 * @author: Maarten
 */
public class ReleaseModel extends AbstractEntityModel<Release> {
    @SpringBean
    private ReleaseService releaseService;

    public ReleaseModel(Release release) {
        super(release);
    }

    public ReleaseModel(Integer releaseId) {
        super(releaseId);
    }

    @Override
    protected Release create() {
        return new Release();
    }

    private Release getRelease() {
        return super.getObject();
    }

    @Override
    protected GenericEntityService<Release> getEntityService() {
        return releaseService;
    }
}
