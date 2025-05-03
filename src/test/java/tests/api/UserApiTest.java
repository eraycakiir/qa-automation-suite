package tests.api;


import io.qameta.allure.Step;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ApiUtils;
import tests.base.BaseApiTest;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseApiTest {

    @Test
    @Description("Verify that the second page of users returns a valid non-empty list and contains correct email format")
    public void testUserListPage2ReturnsValidData() {
        Response response = sendGetUserListRequest();
        validateUserListResponse(response);
    }

    @Step("Send GET request for user list on page 2")
    private Response sendGetUserListRequest() {
        return ApiUtils.get("/api/users?page=2");
    }

    @Step("Validate user list response structure and content")
    private void validateUserListResponse(Response response) {
        response.then().statusCode(200);
        List<Map<String, Object>> users = response.jsonPath().getList("data");

        System.out.println("User List:");
        for (Map<String, Object> user : users) {
            System.out.println("- " + user.get("first_name") + " " + user.get("last_name") + " | " + user.get("email"));
        }

        assertThat("User list should not be empty", users, is(not(empty())));
        String email = response.jsonPath().getString("data[0].email");
        assertThat("Email should contain @", email, containsString("@"));
    }
}