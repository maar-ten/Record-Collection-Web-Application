package nl.laurs.view;

import org.apache.wicket.markup.html.link.Link;

/**
 * Homepage
 */
public class HomePage extends BasePage {

    public HomePage() {
        add(new Link<Void>("enterDataLink") {
            @Override
            public void onClick() {
                setResponsePage(new DataEntryMenuPage(getPage()));
            }
        });
        add(new Link<Void>("viewDataLink") {
            @Override
            public void onClick() {
                setResponsePage(new DataViewMenuPage(getPage()));
            }
        });
    }

    @Override
    protected String getPageTitle() {
        return "Platencollectie";
    }
}
