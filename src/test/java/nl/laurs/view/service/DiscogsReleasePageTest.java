package nl.laurs.view.service;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import static org.junit.Assert.assertEquals;

/**
 * This suite uses Selenium and an embedded Jetty application server for testing the DiscogsReleasePage.
 * <p/>
 * Remember to set the full path to the chrome driver.
 *
 * @author: Maarten Laurs
 */
public class DiscogsReleasePageTest {
    private static final String PATH_TO_CHROME_DRIVER = "path_to_driver";

    private static ChromeDriverService service;
    private static Server server;

    private WebDriver chromeDriver;

    @BeforeClass
    public static void createAndStartService() throws Exception {
        creatAndStartApplicationServer();
        createAndStartChromeDriverService();
    }

    private static void creatAndStartApplicationServer() {
        server = new Server();
        SocketConnector connector = new SocketConnector();

        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setPort(80);
        server.setConnectors(new Connector[] { connector });

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("src/main/webapp");

        server.addHandler(bb);

        try {
            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }

    private static void createAndStartChromeDriverService() throws IOException {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
        service = ChromeDriverService.createDefaultService();
        service.start();
    }

    @AfterClass
    public static void stopService() throws Exception {
        server.stop();
        server.join();
        System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
        service.stop();
    }

    @Before
    public void createDriver() {
        chromeDriver = new ChromeDriver(service);
    }

    @After
    public void stopDriver() {
        chromeDriver.quit();
    }

    @Test
    public void openPage() throws Exception {
        chromeDriver.get("http://localhost/discogs/release/1");

        WebElement pageTitle = chromeDriver.findElement(By.className("pageTitle"));
        assertEquals("Discogs service", pageTitle.getText());
    }
}
