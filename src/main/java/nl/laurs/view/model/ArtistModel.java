package nl.laurs.view.model;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import nl.laurs.domain.Artist;
import nl.laurs.service.ArtistService;
import nl.laurs.service.GenericEntityService;

/**
 * @author: ML
 */
public class ArtistModel extends AbstractEntityModel<Artist> {
    @SpringBean
    private ArtistService artistService;

    public ArtistModel(Artist artist) {
        super(artist);
    }

    @Override
    protected Artist create() {
        return new Artist();
    }

    @Override
    protected GenericEntityService<Artist> getEntityService() {
        return artistService;
    }

    private Artist getArtist() {
        return getObject();
    }

    public IModel<String> getLastnameModel() {
        return new NonDetachableModel<String>() {
            public String getObject() {
                return getArtist().getName();
            }

            public void setObject(String object) {
                getArtist().setName(object);
            }
        };
    }

    public IModel<String> getFirstnameModel() {
        return new NonDetachableModel<String>() {
            public String getObject() {
                return getArtist().getName();
            }

            public void setObject(String object) {
                getArtist().setName(object);
            }
        };
    }
}
