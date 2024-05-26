import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import userpackage.User;
import userpackage.UserPath;
import userpackage.UserResponse;

public class UserTest {
    private static final UserPath userPath = new UserPath();
    private static final UserResponse userResponse = new UserResponse();

    static User user;

    @BeforeClass
    public static void createUniqueUser() {
        user = User.random();
        ValidatableResponse createResponse = userPath.createUser(user);
        userResponse.createdSuccessfully(createResponse, user);
    }

    @DisplayName("Unique User is creating")
    @Test
    public void testUserCreation() {
        // Пользователь уже создан в @BeforeClass, этот тест проверяет, что создание прошло успешно
        assertNotNull(user);
    }

    @DisplayName("Existing user is creating")
    @Test
    public void testExistingUser() {
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
        User userWithoutField = new User(null, "secret123", "Smith");
        ValidatableResponse createResponseForbiddenField = userPath.createUserWithoutField(userWithoutField);
        userResponse.createdWithoutObligatoryField(createResponseForbiddenField);
    }
}
