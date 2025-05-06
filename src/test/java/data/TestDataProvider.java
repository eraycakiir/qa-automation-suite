package data;
import org.testng.annotations.DataProvider;

public class TestDataProvider {
    @DataProvider(name = "validUsers")
    public static Object[][] validUserData() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}
        };
    }

    @DataProvider(name = "invalidUsers")
    public static Object[][] invalidUserData() {
        return new Object[][]{
                {"standard_user", "wrong_password"},
                {"locked_out_user", "secret_sauce"},
                {"", "secret_sauce"},
                {"standard_user", ""},
                {"invalid_user", "invalid_pass"}
        };
    }
}