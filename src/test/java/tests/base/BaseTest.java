package tests.base;

import com.microsoft.playwright.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.*;
import utils.AllureMetadataWriter;
import utils.BrowserFactory;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class BaseTest {
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setupBrowser() {
        browser = BrowserFactory.getBrowser();
    }

    @BeforeMethod
    public void setupPage() {
        context = browser.newContext();
        page = context.newPage();
    }
    @AfterMethod
    public void closeContext() {
        context.close();
    }

    @AfterSuite
    public void closeAll() {
        BrowserFactory.closeAll();
    }
}