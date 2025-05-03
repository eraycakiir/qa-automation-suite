package tests.ui;

import org.testng.annotations.Test;
import tests.base.BaseTest;
import pages.LoginPage;
import io.qameta.allure.Step;
import io.qameta.allure.Description;

import static org.testng.Assert.assertTrue;

public class InvalidLoginTest extends BaseTest {

    @Test
    @Description("Verify that invalid login attempt shows an error message")
    public void testInvalidLoginShowsErrorMessage() {
        LoginPage loginPage = new LoginPage(page);
        navigateToLoginPage(loginPage);
        attemptInvalidLogin(loginPage);
        verifyErrorMessage(loginPage);
    }

    @Step("Navigate to login page")
    private void navigateToLoginPage(LoginPage loginPage) {
        loginPage.navigateToLoginPage();
    }

    @Step("Attempt to log in with invalid credentials")
    private void attemptInvalidLogin(LoginPage loginPage) {
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLogin();
    }

    @Step("Verify error message is visible")
    private void verifyErrorMessage(LoginPage loginPage) {
        assertTrue(loginPage.isErrorMessageVisible(), "Expected error message was not displayed.");
    }
}