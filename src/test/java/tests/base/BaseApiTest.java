// ✅ BaseApiTest.java
package tests.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.ConfigReader;
import io.qameta.allure.testng.AllureTestNg;
import hooks.Hooks;

@Listeners({AllureTestNg.class, Hooks.class})
public class BaseApiTest {

    @BeforeMethod(alwaysRun = true)
    public void setApiBaseUrl() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrlApi");
    }
}