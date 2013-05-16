package nl.laurs.view.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.crypt.Base64;

import nl.laurs.domain.Artist;
import nl.laurs.domain.Release;
import nl.laurs.domain.Track;
import nl.laurs.view.BasePage;
import nl.laurs.view.model.ReleaseModel;

/**
 * @author: Maarten
 */
public class ReleasePage extends BasePage {

    public ReleasePage(Release release) {
        setDefaultModel(new ReleaseModel(release));
    }

    public ReleasePage(PageParameters params) {
        Integer releaseId = params.get("release").toInteger();
        setDefaultModel(new ReleaseModel(releaseId));
    }

    private Release getRelease() {
        return (Release) getDefaultModelObject();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initPage();
    }

    private void initPage() {
        Release release = getRelease();
        add(createImage("releaseImage"));
        add(new Label("releaseTitle", release.getTitle()).setRenderBodyOnly(true));
        add(new Label("releaseLabel", release.getLabel().getName()).setRenderBodyOnly(true));
        add(new Label("releaseCatno", release.getCatno()).setRenderBodyOnly(true));
        add(new Label("releaseYear", String.valueOf(release.getYear())).setRenderBodyOnly(true));
        add(createArtistListView("artistList"));
        add(createTrackListView("trackList"));
    }

    private Component createImage(String id) {
        Release release = getRelease();

        Component image = new WebMarkupContainer(id).setVisible(release.getImage() != null);
        image.add(new AttributeModifier("src", createBase64Model(release)));
        return image;
    }

    private IModel<?> createBase64Model(Release release) {
        StringBuilder b = new StringBuilder();
        b.append("data:").append(release.getImageType()).append(";base64,");
        b.append(Base64.encodeBase64String(release.getImage()));
        return Model.of(b.toString());
    }

    private Component createTrackListView(String id) {
        LoadableDetachableModel<List<Track>> listModel = new LoadableDetachableModel<List<Track>>() {
            @Override
            protected List<Track> load() {
                ArrayList<Track> tracks = new ArrayList<>(getRelease().getTracks());
                Collections.sort(tracks);
                return tracks;
            }
        };
        return new ListView<Track>(id, listModel) {
            @Override
            protected void populateItem(ListItem<Track> item) {
                item.add(new Label("trackPosition", item.getModelObject().getPosition()).setRenderBodyOnly(true));
                item.add(new Label("trackTitle", item.getModelObject().getTitle()).setRenderBodyOnly(true));
            }
        };
    }

    private Component createArtistListView(String id) {
        LoadableDetachableModel<List<Artist>> listModel = new LoadableDetachableModel<List<Artist>>() {
            @Override
            protected List<Artist> load() {
                return new ArrayList<>(getRelease().getArtists());
            }
        };
        return new ListView<Artist>(id, listModel) {
            @Override
            protected void populateItem(ListItem<Artist> item) {
                item.add(new Label("artistName", item.getModelObject().getName()).setRenderBodyOnly(true));
            }
        };
    }

    @Override
    protected String getPageTitle() {
        return "Release";
    }
}
