package nl.laurs.view.overview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.AbstractPageableView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.crypt.Base64;

import nl.laurs.domain.Release;
import nl.laurs.service.ReleaseService;
import nl.laurs.view.BasePage;
import nl.laurs.view.domain.ReleasePage;
import nl.laurs.view.model.ReleaseModel;

/**
 * @author: maarten
 */
public class ReleaseOverviewPage extends BasePage {
    @SpringBean
    private ReleaseService releaseService;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        addPageableOverview();
    }

    private void addPageableOverview() {
        AbstractPageableView<Release> pageableView = new AbstractPageableView<Release>("releaseList") {
            @Override
            protected Iterator<IModel<Release>> getItemModels(long offset, long size) {
                List<IModel<Release>> models = new ArrayList<>();
                for (Release release : releaseService.getSome(offset, size, Release.TITLE)) {
                    models.add(new ReleaseModel(release));
                }
                return models.iterator();
            }

            @Override
            protected long internalGetItemCount() {
                return releaseService.countAll();
            }

            @Override
            protected void populateItem(Item<Release> item) {
                item.add(createReleaseLink(item));
            }

            private Link<Release> createReleaseLink(ListItem<Release> item) {
                Release release = item.getModelObject();
                ReleaseModel valueModel = new ReleaseModel(release);

                Link<Release> link = new Link<Release>("releaseLink", valueModel) {
                    @Override
                    public void onClick() {
                        setResponsePage(new ReleasePage(getModelObject()));
                    }
                };
                link.add(new Label("releaseLabel", release.getTitle()));
                link.add(createImage(release));

                return link;
            }

            private Component createImage(Release release) {
                WebMarkupContainer image = new WebMarkupContainer("releaseImage");
                image.add(new AttributeModifier("src", createBase64Model(release)));
                image.setVisible(release.getImage() != null);
                return image;
            }

            private IModel<?> createBase64Model(Release release) {
                StringBuilder b = new StringBuilder();
                b.append("data:").append(release.getImageType()).append(";base64,");
                b.append(Base64.encodeBase64String(release.getImage()));
                return Model.of(b.toString());
            }
        };
        pageableView.setItemsPerPage(10);

        add(pageableView);
        add(new PagingNavigator("pageNavigator", pageableView));
    }

    @Override
    protected String getPageTitle() {
        return "Releases";
    }
}
