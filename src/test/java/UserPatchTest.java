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
        // Создание нового пользователя перед каждым тестом
        User user = User.random();
        ValidatableResponse createResponse = userPath.createUser(user);

        // Получение токена доступа из ответа на создание пользователя
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
        // Попытка логина созданного пользователя
       // UserCreds creds = UserCreds.from(UserTest.user);
        //ValidatableResponse loginResponse = userPath.loginUser(creds);

        // Проверка успешного логина и получение accessToken
       // String token = userResponse.loggedInSuccessfully(loginResponse);
       // assertNotNull("Access token should not be null", token);
       // accessToken = token;

        // Изменение имени пользователя
        String newName = "newUserName";
        ValidatableResponse changingResponseName = userPath.changingName(accessToken, newName);

        // Проверка успешного изменения имени
        userResponse.authUserIsChangingName(changingResponseName, newName);
    }
    @DisplayName("Authorized user can change email")
    @Test
    public void authUserChangeEmail() {
        // Попытка логина созданного пользователя
       // UserCreds creds = UserCreds.from(UserTest.user);
       // ValidatableResponse loginResponse = userPath.loginUser(creds);

        // Проверка успешного логина и получение accessToken
       // String token = userResponse.loggedInSuccessfully(loginResponse);
       // assertNotNull("Access token should not be null", token);
       // accessToken = token;

        // Изменение имени пользователя
        String newEmail = "newuseremail@example.com";
        ValidatableResponse changingResponseEmail = userPath.changingEmail(accessToken, newEmail);

        // Проверка успешного изменения имени
        userResponse.authUserIsChangingEmail(changingResponseEmail, newEmail);
    }
    @DisplayName("Authorized user can change password")
    @Test
    public void authUserChangePassword() {
        // Попытка логина созданного пользователя
       // UserCreds creds = UserCreds.from(UserTest.user);
       // ValidatableResponse loginResponse = userPath.loginUser(creds);

        // Проверка успешного логина и получение accessToken
       // String token = userResponse.loggedInSuccessfully(loginResponse);
        //assertNotNull("Access token should not be null", token);
        //accessToken = token;

        // Изменение имени пользователя
        String newPassword = "n121212121";
        ValidatableResponse changingResponsePassword = userPath.changingPassword(accessToken, newPassword);

        // Проверка успешного изменения имени
        userResponse.authUserIsChangingPassword(changingResponsePassword, newPassword);
    }
    @Test
    @DisplayName("Not authorized user can change name")
    public void notAuthUserChangeName() {
        // Изменение имени пользователя
        String newName = "newUserName";
        ValidatableResponse changingResponseName = userPath.changingNameNotAuth(newName);

        // Проверка ответа на запрос изменения имени
        userResponse.notAuthUserIsChangingName(changingResponseName, newName);
    }

}
