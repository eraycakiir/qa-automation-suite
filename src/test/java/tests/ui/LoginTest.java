package tests.ui;

import data.TestDataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import tests.base.BaseTest;
import io.qameta.allure.Step;
import io.qameta.allure.Description;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "validUsers", dataProviderClass = TestDataProvider.class, groups = {"regression", "ui-only"})
    @Description("Verify that valid users can log in successfully")
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

    @Step("Perform login")
    private void performLogin(LoginPage loginPage, String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Step("Verify login success")
    private void verifyLoginSuccess() {
        assertTrue(page.url().contains("inventory.html"), "Login failed.");
    }
}
