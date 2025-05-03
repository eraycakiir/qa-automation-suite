package utils;

import com.microsoft.playwright.*;
import java.util.Properties;

public class BrowserFactory {
    private static Playwright playwright;
    private static Browser browser;

    public static Browser getBrowser() {
        if (browser == null) {
            playwright = Playwright.create();
            boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));

        }
        return browser;
    }

    public static void closeAll() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}