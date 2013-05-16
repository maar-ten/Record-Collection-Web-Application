package nl.laurs.view.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import nl.laurs.discogs.domain.DiscogsReleaseImage;
import nl.laurs.discogs.domain.DiscogsReleaseLabel;
import nl.laurs.domain.Artist;
import nl.laurs.domain.Release;
import nl.laurs.domain.Track;
import nl.laurs.service.DiscogsApiService;
import nl.laurs.service.ReleaseService;
import nl.laurs.view.BasePage;
import nl.laurs.view.component.ImageSelector;
import nl.laurs.view.domain.ReleasePage;

/**
 * This page is the entrance page for outside users to extract release information from the Discogs database.
 * <p/>
 * The page expects the first parameter to be an integer, which should be the id of a Discogs release entry
 *
 * @author: ML
 */
public class DiscogsReleasePage extends BasePage {
    public static final Logger LOG = Logger.getLogger(DiscogsReleasePage.class);

    @SpringBean
    private DiscogsApiService discogsApiService;
    @SpringBean
    private ReleaseService releaseService;
    private Map<String, Object> releaseDataMap;

    private DiscogsReleaseImage releaseImage;
    private DiscogsReleaseLabel releaseLabel;

    public DiscogsReleasePage(PageParameters pageParameters) {
        Integer releaseId = getReleaseId(pageParameters);
        if (releaseId == null) {
            error("Onjuiste invoer ontvangen");
            return;
        }

        if (existingRelease(releaseId)) {
            error("Release bestaat al");
            return;
        }

        releaseDataMap = discogsApiService.getReleaseDataMap(releaseId);
        if (releaseDataMap == null || releaseDataMap.size() == 0) {
            error("Data kon niet worden opgehaald");
            return;
        }

        Release release = discogsApiService.createRelease(releaseDataMap);
        if (release == null) {
            error("Data kon niet worden verwerkt");
            return;
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (hasErrorMessage()) {
            add(new EmptyPanel("formContainer").setVisible(false));
        }
        else {
            Fragment fragment = new Fragment("formContainer", "formFragment", DiscogsReleasePage.this);
            fragment.add(new DiscogsEditReleaseForm("form"));
            fragment.setRenderBodyOnly(true);
            add(fragment);
        }
    }

    private boolean existingRelease(Integer releaseId) {
        return releaseService.getByDiscogsId(releaseId) != null;
    }

    /**
     * @param pageParameters
     * @return null if there isn't a release id
     */
    private Integer getReleaseId(PageParameters pageParameters) {
        StringValue releaseId = pageParameters.get("release");
        try {
            return releaseId.toInteger();
        }
        catch (Exception e) {
            LOG.error("Error because of release parameter: " + releaseId, e);
        }
        return null;
    }

    @Override
    protected String getPageTitle() {
        return "Discogs service";
    }

    private class DiscogsEditReleaseForm extends Form<Release> {
        public DiscogsEditReleaseForm(String id) {
            super(id);
            add(createImageSelector("imageSelector"));
            addReleaseOverviewData();
            add(createLabelChoice("labelDropDownChoice"));
            add(createTrackListView("trackList"));
            add(createSaveButton("saveButton"));
        }

        private void addReleaseOverviewData() {
            Release release = discogsApiService.createRelease(releaseDataMap);

            add(new Label("releaseTitle", release.getTitle()).setRenderBodyOnly(true));
            add(new Label("releaseYear", String.valueOf(release.getYear())).setRenderBodyOnly(true));
            add(createArtistListView("artistList"));
        }

        private Component createArtistListView(String id) {
            IModel<List<Artist>> valueList = new AbstractReadOnlyModel<List<Artist>>() {
                @Override
                public List<Artist> getObject() {
                    return new ArrayList<>(discogsApiService.createRelease(releaseDataMap).getArtists());
                }
            };
            return new ListView<Artist>(id, valueList) {
                @Override
                protected void populateItem(ListItem<Artist> item) {
                    item.add(new Label("artistName", item.getModelObject().getName()).setRenderBodyOnly(true));
                }
            };
        }

        private Component createTrackListView(String id) {
            IModel<List<Track>> valueList = new AbstractReadOnlyModel<List<Track>>() {
                @Override
                public List<Track> getObject() {
                    ArrayList<Track> tracks = new ArrayList<>(
                            discogsApiService.createRelease(releaseDataMap).getTracks());
                    Collections.sort(tracks);
                    return tracks;
                }
            };
            return new ListView<Track>(id, valueList) {
                @Override
                protected void populateItem(ListItem<Track> item) {
                    item.add(new Label("trackPosition", item.getModelObject().getPosition()).setRenderBodyOnly(true));
                    item.add(new Label("trackTitle", item.getModelObject().getTitle()).setRenderBodyOnly(true));
                }
            };
        }

        private Component createLabelChoice(String id) {
            Model<DiscogsReleaseLabel> valueModel = new Model<DiscogsReleaseLabel>() {
                @Override
                public DiscogsReleaseLabel getObject() {
                    return releaseLabel;
                }

                @Override
                public void setObject(DiscogsReleaseLabel object) {
                    releaseLabel = object;
                }
            };
            AbstractReadOnlyModel<List<DiscogsReleaseLabel>> choicesModel = new AbstractReadOnlyModel<List<DiscogsReleaseLabel>>() {
                @Override
                public List<DiscogsReleaseLabel> getObject() {
                    return discogsApiService.getDiscogsReleaseLabels(releaseDataMap);
                }
            };
            IChoiceRenderer<DiscogsReleaseLabel> renderer = new IChoiceRenderer<DiscogsReleaseLabel>() {
                public Object getDisplayValue(DiscogsReleaseLabel object) {
                    return object.getName() + " (" + object.getCatno() + ")";
                }

                public String getIdValue(DiscogsReleaseLabel object, int index) {
                    return String.valueOf(index);
                }
            };
            DropDownChoice<DiscogsReleaseLabel> dropDownChoice = new DropDownChoice<>(id, valueModel,
                    choicesModel, renderer);
            dropDownChoice.setLabel(new Model<>("Label"));
            dropDownChoice.setNullValid(false);
            dropDownChoice.setRequired(true);
            return dropDownChoice;
        }

        private Component createSaveButton(String id) {
            return new Button(id) {
                @Override
                public void onSubmit() {
                    Release release = discogsApiService.createRelease(releaseDataMap, releaseLabel, releaseImage);
                    releaseService.save(release);
                    info("Release opgeslagen");
                    setResponsePage(new ReleasePage(release));
                }
            };
        }

        private Component createImageSelector(String id) {
            IModel<? extends DiscogsReleaseImage> valueModel = new Model<DiscogsReleaseImage>() {
                @Override
                public DiscogsReleaseImage getObject() {
                    return releaseImage;
                }

                @Override
                public void setObject(DiscogsReleaseImage object) {
                    releaseImage = object;
                }
            };
            IModel<List<DiscogsReleaseImage>> listModel = new AbstractReadOnlyModel<List<DiscogsReleaseImage>>() {
                @Override
                public List<DiscogsReleaseImage> getObject() {
                    return discogsApiService.getReleaseImages(releaseDataMap);
                }
            };
            return new ImageSelector<DiscogsReleaseImage>(id, valueModel, listModel) {
                @Override
                protected String getUri(DiscogsReleaseImage object) {
                    return object.getUri150();
                }
            }.setNullValid(true);
        }
    }
}
