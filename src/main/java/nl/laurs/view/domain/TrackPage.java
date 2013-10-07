package nl.laurs.view.domain;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;

import nl.laurs.domain.Track;
import nl.laurs.view.BasePage;
import nl.laurs.view.model.TrackModel;

/**
 * @author: ML
 */
public class TrackPage extends BasePage {
    public TrackPage(Page backPage) {
        super(backPage);
        init(new TrackModel(new Track()), backPage);
    }

    private void init(TrackModel trackModel, Page backPage) {
        Form<Track> form = new Form<>("form", trackModel);
        add(form);
    }

    @Override
    protected String getPageTitle() {
        return "Track";
    }
}
