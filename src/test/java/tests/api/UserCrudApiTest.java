// 📄 UserCrudApiTest.java – API testleri (CRUD)
package tests.api;

import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import tests.base.BaseApiTest;
import utils.ApiUtils;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserCrudApiTest extends BaseApiTest {

    @Test
    @Description("Create a user with name and job and validate response")
    public void testCreateUserSuccessfully() {
        Map<String, String> payload = createUserPayload();
        Response response = createUser(payload);
        validateUserCreation(response);
    }

    @Step("Create user payload")
    private Map<String, String> createUserPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "morpheus");
        payload.put("job", "leader");
        return payload;
    }

    @Step("Send POST request to create user")
    private Response createUser(Map<String, String> payload) {
        Response response = ApiUtils.post("/api/users", payload);
        response.prettyPrint();
        return response;
    }

    @Step("Validate user creation response")
    private void validateUserCreation(Response response) {
        response.then().statusCode(201);
        assertThat(response.jsonPath().getString("id"), notNullValue());
        assertThat(response.jsonPath().getString("createdAt"), containsString("202"));
    }

    @Test
    @Description("Update a user and verify updated job title")
    public void testUpdateUserSuccessfully() {
        Map<String, String> payload = updateUserPayload();
        Response response = updateUser(payload);
        validateUserUpdate(response);
    }

    @Step("Create update payload")
    private Map<String, String> updateUserPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "morpheus");
        payload.put("job", "zion resident");
        return payload;
    }

    @Step("Send PUT request to update user")
    private Response updateUser(Map<String, String> payload) {
        Response response = ApiUtils.put("/api/users/2", payload);
        response.prettyPrint();
        return response;
    }

    @Step("Validate user update response")
    private void validateUserUpdate(Response response) {
        response.then().statusCode(200);
        assertThat(response.jsonPath().getString("job"), equalTo("zion resident"));
        assertThat(response.jsonPath().getString("updatedAt"), containsString("202"));
    }

    @Test
    @Description("Delete user and confirm status code is 204")
    public void testDeleteUserSuccessfully() {
        Response response = deleteUser();
        validateUserDeletion(response);
    }

    @Step("Send DELETE request to delete user")
    private Response deleteUser() {
        Response response = ApiUtils.delete("/api/users/2");
        response.prettyPrint();
        return response;
    }

    @Step("Validate user deletion response")
    private void validateUserDeletion(Response response) {
        response.then().statusCode(204);
    }
}
