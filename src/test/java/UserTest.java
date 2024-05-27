import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import userpackage.User;
import userpackage.UserPath;
import userpackage.UserResponse;

public class UserTest {
    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();

    static User user;
    static User userWithoutField;
    static String accessToken;


    @After
    public void deleteUser() {
        if (accessToken != null && !accessToken.isEmpty()) {
            ValidatableResponse response = userPath.deleteUser(accessToken);
            userResponse.deletedSuccesfully(response);
        }
    }
    // Вспомогательный метод в классе UserTest
    public String getAccessToken() {
        return accessToken;
    }



    @DisplayName("Unique User is creating")
    @Test
    public void testUser() {
        user = User.random();
        ValidatableResponse createResponse = userPath.createUser(user);
        userResponse.createdSuccessfully(createResponse, user);
    }

    @DisplayName("Existing user is creating")
    @Test
    public void testExistingUser() {
        var user = User.random();
        ValidatableResponse createResponse = userPath.createUser(user);
        userResponse.createdSuccessfully(createResponse, user);

        String email = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();

        User user2 = new User(email, password, name);

        ValidatableResponse createResponseForbidden = userPath.createExistingUser(user2);
        userResponse.createdUnSuccessfully(createResponseForbidden);
    }

    @DisplayName("User without obligatory field is creating")
    @Test
    public void testUserObligatoryField() {
        userWithoutField = new User(null, "secret123", "Smith");
        ValidatableResponse createResponseForbiddenField = userPath.createUserWithoutField(userWithoutField);
        userResponse.createdWithoutObligatoryField(createResponseForbiddenField);
    }

}
