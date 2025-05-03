package tests.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import utils.AllureMetadataWriter;
import utils.ConfigReader;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({AllureTestNg.class})
public class BaseApiTest {

    @BeforeMethod(alwaysRun = true)
    public void setApiBaseUrl() {
        RestAssured.baseURI = ConfigReader.getProperty("baseUrlApi");
    }

}
