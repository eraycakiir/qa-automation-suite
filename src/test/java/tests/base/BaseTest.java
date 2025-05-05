// ✅ Updated BaseTest.java
package tests.base;

import com.microsoft.playwright.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.*;
import utils.BrowserFactory;
import org.testng.annotations.Listeners;
import hooks.Hooks;  // Include Hooks

@Listeners({AllureTestNg.class, Hooks.class})
public class BaseTest {
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setupBrowser() {
        System.setProperty("allure.results.directory", "allure-results");
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
