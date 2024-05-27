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

public class UserPatchTest {
    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();
    static String accessToken;

    @Before
    public void setUp() {
        User user = User.random();
        ValidatableResponse createResponse = userPath.createUser(user);

        accessToken = userResponse.loggedInSuccessfully(createResponse);
        assertNotNull("Access token should not be null", accessToken);
    }

    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = userPath.deleteUser(accessToken);
            userResponse.deletedSuccesfully(response);
        }
    }

    @DisplayName("Authorized user can change name")
    @Test
    public void authUserChangeName() {

        String newName = "newUserName";
        ValidatableResponse changingResponseName = userPath.changingName(accessToken, newName);

        userResponse.authUserIsChangingName(changingResponseName, newName);
    }
    @DisplayName("Authorized user can change email")
    @Test
    public void authUserChangeEmail() {

        String newEmail = "newuseremail@example.com";
        ValidatableResponse changingResponseEmail = userPath.changingEmail(accessToken, newEmail);

        userResponse.authUserIsChangingEmail(changingResponseEmail, newEmail);
    }
    @DisplayName("Authorized user can change password")
    @Test
    public void authUserChangePassword() {

        String newPassword = "n121212121";
        ValidatableResponse changingResponsePassword = userPath.changingPassword(accessToken, newPassword);

        userResponse.authUserIsChangingPassword(changingResponsePassword, newPassword);
    }
    @Test
    @DisplayName("Not authorized user can change name")
    public void notAuthUserChangeName() {

        String newName = "newUserName";
        ValidatableResponse changingResponseName = userPath.changingNameNotAuth(newName);

        userResponse.notAuthUserIsChangingName(changingResponseName, newName);
    }
    @Test
    @DisplayName("Not authorized user can change email")
    public void notAuthUserChangeEmail() {

        String newEmail = "newuseremail@example.com";
        ValidatableResponse changingResponseEmail = userPath.changingEmailNotAuth(newEmail);

        userResponse.notAuthUserIsChangingEmail(changingResponseEmail, newEmail);
    }
    @Test
    @DisplayName("Not authorized user can change password")
    public void notAuthUserChangePassword() {

        String newPassword = "newpassword";
        ValidatableResponse changingResponsePassword = userPath.changingPasswordNotAuth(newPassword);

        userResponse.notAuthUserIsChangingPassword(changingResponsePassword, newPassword);
    }

}
