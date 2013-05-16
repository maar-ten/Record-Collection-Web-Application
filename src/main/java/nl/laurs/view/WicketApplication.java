package nl.laurs.view;

import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import nl.laurs.view.domain.ReleasePage;
import nl.laurs.view.overview.ReleaseOverviewPage;
import nl.laurs.view.service.DiscogsReleasePage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start
 * class.
 * <p/>
 * I have added a {@link SpringComponentInjector} to allow IOC on all Wicket components
 *
 * @see nl.laurs.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    @Override
    protected void init() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getMarkupSettings().setStripWicketTags(true);

        mountPages();
    }

    private void mountPages() {
        mount(new MountedMapper("/discogs", DiscogsReleasePage.class, new UrlPathPageParametersEncoder()));
        mount(new MountedMapper("/release", ReleasePage.class, new UrlPathPageParametersEncoder()));
        mount(new MountedMapper("/releases", ReleaseOverviewPage.class, new UrlPathPageParametersEncoder()));
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }
}
