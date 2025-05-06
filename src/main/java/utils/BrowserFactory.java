package utils;

import com.microsoft.playwright.*;

import java.util.List;

public class BrowserFactory {
    private static Playwright playwright;
    private static Browser browser;

    /** seferlik (singleton) Chromium . */
    public static Browser getBrowser() {
        if (browser == null) {
            playwright = Playwright.create();

            // ► headless ayarını config.properties’ten çek
            String headlessStr = ConfigReader.getProperty("headless");
            boolean headless = headlessStr == null || Boolean.parseBoolean(headlessStr);

            BrowserType.LaunchOptions opts = new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setArgs(List.of(
                            "--no-sandbox",
                            "--disable-dev-shm-usage"
                    ));

            browser = playwright.chromium().launch(opts);
        }
        return browser;
    }

    /** Playwright CLOSED. */
    public static void closeAll() {
        if (browser != null)    browser.close();
        if (playwright != null) playwright.close();
    }
}
