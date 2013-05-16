package nl.laurs.view.model;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;

import nl.laurs.domain.AbstractGenericEntity;
import nl.laurs.service.GenericEntityService;

/**
 * @author: Maarten
 */
public abstract class AbstractEntityModel<T extends AbstractGenericEntity> extends LoadableDetachableModel<T> {
    private Integer entityId;

    public AbstractEntityModel(Integer releaseId) {
        Injector.get().inject(this);
        this.entityId = releaseId;
    }

    protected abstract GenericEntityService<T> getEntityService();

    public AbstractEntityModel(T entity) {
        Injector.get().inject(this);
        this.entityId = entity.getId();
    }

    @Override
    protected T load() {
        if (entityId != null) {
            return getEntityService().get(entityId);
        }
        return create();
    }

    protected abstract T create();
}
