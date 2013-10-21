package nl.laurs.view;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.Link;

import nl.laurs.view.domain.ArtistPage;

/**
 * Menu page with links to domain object's CRUD pages.
 *
 * @author: Maarten Laurs
 */
public class DataEntryMenuPage extends BasePage {

    public DataEntryMenuPage(final Page backPage) {
        super(backPage);
        add(addArtistLink());
    }

    private Link<Void> addArtistLink() {
        return new Link<Void>("addArtistLink") {
            @Override
            public void onClick() {
                setResponsePage(new ArtistPage(getPage()));
            }
        };
    }

    @Override
    protected String getPageTitle() {
        return "Gegevens invoeren";
    }
}
