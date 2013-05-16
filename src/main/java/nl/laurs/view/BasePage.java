package nl.laurs.view;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

/**
 * Basic web page containing stylesheet reference, a title bar and a back page button.
 *
 * @author: ML
 */
public abstract class BasePage extends WebPage {

    protected abstract String getPageTitle();

    public BasePage() {
        init(null);
    }

    public BasePage(final Page backPage) {
        init(backPage);
    }

    private void init(final Page backPage) {
        add(new Link<Page>("backButton") {
            @Override
            public void onClick() {
                setResponsePage(backPage);
            }
        }.setVisible(backPage != null));
        add(new FeedbackPanel("feedbackMessages") {
            @Override
            public boolean isVisible() {
                return anyMessage();
            }
        });
    }

    public BasePage(IModel<?> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("pageTitle", getPageTitle()));
    }
}
