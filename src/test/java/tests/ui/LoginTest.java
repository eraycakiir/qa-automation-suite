package tests.ui;

import data.TestDataProvider;
import org.testng.annotations.Test;
import tests.base.BaseTest;
import pages.LoginPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "validUsers", dataProviderClass = TestDataProvider.class)
    @Description("Verify that valid users can successfully log in and reach the inventory page.")
    public void testLoginSuccess(String username, String password) {
        LoginPage loginPage = new LoginPage(page);
        navigateToLoginPage(loginPage);
        performLogin(loginPage, username, password);
        verifyLoginSuccess();
    }

    @Step("Navigate to login page")
    private void navigateToLoginPage(LoginPage loginPage) {
        loginPage.navigateToLoginPage();
    }

    @Step("Enter username and password")
    private void performLogin(LoginPage loginPage, String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Step("Verify login redirection to inventory page")
    private void verifyLoginSuccess() {
        assertTrue(page.url().contains("inventory.html"), "Login failed or page was not redirected.");
    }
}