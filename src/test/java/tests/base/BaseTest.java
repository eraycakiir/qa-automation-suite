package tests.base;

import com.microsoft.playwright.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.*;
import utils.BrowserFactory;
import org.testng.annotations.Listeners;
import hooks.Hooks;

@Listeners({AllureTestNg.class, Hooks.class})
public abstract class BaseTest {  // ✅ soyut sınıf
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass(alwaysRun = true)
    public void setupBrowser() {
        System.setProperty("allure.results.directory", "allure-results");
        browser = BrowserFactory.getBrowser();
        System.out.println("✅ Browser launched");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupPage() {
        context = browser.newContext();
        page = context.newPage();
        System.out.println("✅ Page created: " + page);
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext() {
        if (context != null) {
            context.close();
            System.out.println("🧹 Context closed");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void closeAll() {
        BrowserFactory.closeAll();
        System.out.println("🧹 Playwright closed");
    }
}
