package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.qameta.allure.Attachment;

public class ApiUtils {

    @Attachment(value = "{title}", type = "application/json")
    public static String logResponse(String title, String responseBody) {
        return responseBody;
    }

    @Step("Send GET request to {endpoint}")
    @Description("Performs a GET request to the given endpoint and returns the response.")
    public static Response get(String endpoint) {
        Response response = given()
                .header("x-api-key", ConfigReader.getProperty("apiKey"))
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
        logResponse("GET Response", response.getBody().asPrettyString());
        return response;
    }

    @Step("Send POST request to {endpoint}")
    @Description("Performs a POST request with a JSON body and returns the response.")
    public static Response post(String endpoint, Object body) {
        Response response = given()
                .header("x-api-key", ConfigReader.getProperty("apiKey"))
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(endpoint);
        logResponse("POST Response", response.getBody().asPrettyString());
        return response;
    }

    @Step("Send PUT request to {endpoint}")
    @Description("Performs a PUT request with a JSON body and returns the response.")
    public static Response put(String endpoint, Object body) {
        Response response = given()
                .header("x-api-key", ConfigReader.getProperty("apiKey"))
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(endpoint);
        logResponse("PUT Response", response.getBody().asPrettyString());
        return response;
    }

    @Step("Send DELETE request to {endpoint}")
    @Description("Performs a DELETE request and returns the response.")
    public static Response delete(String endpoint) {
        Response response = given()
                .header("x-api-key", ConfigReader.getProperty("apiKey"))
                .when()
                .delete(endpoint);
        logResponse("DELETE Response", response.getBody().asPrettyString());
        return response;
    }
}
