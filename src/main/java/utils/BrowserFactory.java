package utils;

import com.microsoft.playwright.*;

import java.util.List;

public class BrowserFactory {
    private static Playwright playwright;
    private static Browser browser;

    /** Tek seferlik (singleton) Chromium başlatır. */
    public static Browser getBrowser() {
        if (browser == null) {
            playwright = Playwright.create();

            // ► headless ayarını config.properties’ten çek
            String headlessStr = ConfigReader.getProperty("headless"); // null olabilir
            boolean headless = headlessStr == null || Boolean.parseBoolean(headlessStr);

            BrowserType.LaunchOptions opts = new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setArgs(List.of(
                            "--no-sandbox",                 // Docker uyumu
                            "--disable-dev-shm-usage"       // bellek hatalarını azaltır
                    ));

            browser = playwright.chromium().launch(opts);
        }
        return browser;
    }

    /** Playwright kaynaklarını kapatır. */
    public static void closeAll() {
        if (browser != null)    browser.close();
        if (playwright != null) playwright.close();
    }
}
