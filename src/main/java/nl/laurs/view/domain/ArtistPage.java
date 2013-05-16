package nl.laurs.view.domain;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponentLabel;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.spring.injection.annot.SpringBean;

import nl.laurs.domain.Artist;
import nl.laurs.service.ArtistService;
import nl.laurs.view.BasePage;
import nl.laurs.view.model.ArtistModel;

/**
 * Page for editing an Artist
 *
 * @author: ML
 */
public class ArtistPage extends BasePage {
    @SpringBean
    private ArtistService artistService;

    public ArtistPage(Page backPage) {
        super(backPage);
        init(new ArtistModel(new Artist()), backPage);
    }

    public ArtistPage(Artist artist, Page backPage) {
        super(backPage);
        init(new ArtistModel(artist), backPage);
    }

    private void init(ArtistModel artistModel, final Page backPage) {
        final Form<Artist> form = new Form<Artist>("form", artistModel);
        add(form);

        TextField lastnameField = new RequiredTextField<String>("lastnameField", artistModel.getLastnameModel());
        form.add(lastnameField);
        form.add(new FormComponentLabel("lastnameLabel", lastnameField));

        TextField firstnameField = new TextField<String>("firstnameField", artistModel.getFirstnameModel());
        form.add(firstnameField);
        form.add(new FormComponentLabel("firstnameLabel", firstnameField));

        form.add(new SubmitLink("saveButton") {
            @Override
            public void onSubmit() {
                Artist artist = form.getModelObject();
                if (duplicateExists(artist)) {
                    error("Artiest bestaat al");
                    return;
                }
                artistService.save(artist);
                setResponsePage(backPage);
            }

            private boolean duplicateExists(Artist newArtist) {
                return artistService.findByName(newArtist.getName()) != null;
            }
        });
    }

    @Override
    protected String getPageTitle() {
        return "Artiest";
    }
}
