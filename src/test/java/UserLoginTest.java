import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import userpackage.User;
import userpackage.UserCreds;
import userpackage.UserPath;
import userpackage.UserResponse;

import static org.junit.Assert.assertNotNull;

public class UserLoginTest {
    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();
    static String accessToken;

    @Before
    public void setUp() {
        UserTest userTest = new UserTest();
        userTest.testUser();
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = userPath.deleteUser(accessToken);
            userResponse.deletedSuccesfully(response);
        }
    }

    @DisplayName("Existing user can login")
    @Test
    public void userLoginSuccess() {
        UserCreds creds = UserCreds.from(UserTest.user);
        ValidatableResponse loginResponse = userPath.loginUser(creds);

        String token = userResponse.loggedInSuccessfully(loginResponse);
        assertNotNull("Access token should not be null", token);
        accessToken = token;
    }

    @DisplayName("User login with incorrect email and password")
    @Test
    public void userLoginWithIncorrectEmailPassword() {
        UserCreds creds = UserCreds.from(UserTest.user);
        creds.setEmail("incorrect@example.com");
        creds.setPassword("incorrectPassword");
        ValidatableResponse loginResponseUnauthorized = userPath.loginIncorrectEmailPassword(creds);
        userResponse.loginWithIncorrectData(loginResponseUnauthorized);
    }
}
