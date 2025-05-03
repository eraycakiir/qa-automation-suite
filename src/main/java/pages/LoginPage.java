package pages;

import com.microsoft.playwright.Page;
import utils.ConfigReader;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToLoginPage() {
        String url = ConfigReader.getProperty("baseUrl");
        page.navigate(url);
    }
    public boolean isErrorMessageVisible() {
        return page.isVisible("[data-test='error']");
    }


    public void enterUsername(String username) {
        page.fill("#user-name", username);
    }

    public void enterPassword(String password) {
        page.fill("#password", password);
    }

    public void clickLogin() {
        page.click("#login-button");
    }
}