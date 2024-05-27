package userpackage;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
public class UserResponse {
    @Step("Check user can login as an existing")
    public String loggedInSuccessfully(ValidatableResponse loginResponse) {
        boolean success = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(success);

        String accessToken = loginResponse.extract().path("accessToken");
        assertNotNull(accessToken);

        return accessToken;
    }
    @Step("Check creating unique user")
    public void createdSuccessfully(ValidatableResponse createResponse, User expectedUser) {
        boolean success = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("success");
        assertTrue(success);

        String email = createResponse.extract().path("user.email");
        String name = createResponse.extract().path("user.name");
        String accessToken = createResponse.extract().path("accessToken");
        String refreshToken = createResponse.extract().path("refreshToken");

        assertEquals(expectedUser.getEmail().toLowerCase(), email.toLowerCase());
        assertEquals(expectedUser.getName(), name);
        assertNotNull(accessToken);
        assertNotNull(refreshToken);
    }

        @Step("Check courier created without obligatory field")
        public void createdWithoutObligatoryField (ValidatableResponse createResponseForbiddenField){
            String errorMessage = createResponseForbiddenField
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                    .extract()
                    .path("message");
            assertEquals("Email, password and name are required fields", errorMessage);
        }

        @Step("Check existing user created unsuccessfully")
        public void createdUnSuccessfully (ValidatableResponse createResponseForbidden){
            String errorMessage = createResponseForbidden
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                    .extract()
                    .path("message");
            assertEquals("User already exists", errorMessage);
        }
        @Step("Check courier cannot login with incorrect email and password")
        public void loginWithIncorrectData (ValidatableResponse loginResponseUnauthorized){
            String errorMessage = loginResponseUnauthorized
                    .assertThat()
                    .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                    .extract()
                    .path("message");
            assertEquals("email or password are incorrect", errorMessage);
        }
    @Step("Check authorized user can change name")
    public void authUserIsChangingName(ValidatableResponse changingResponseName, String newName) {
        changingResponseName
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.name", equalTo(newName));
    }
    @Step("Check authorized user can change email")
    public void authUserIsChangingEmail(ValidatableResponse changingResponseEmail, String newEmail) {
        changingResponseEmail
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(newEmail));
    }

    @Step("Check authorized user can change password")
    public void authUserIsChangingPassword(ValidatableResponse changingResponsePassword, String newPassword) {
        changingResponsePassword
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true));
    }
    @Step("Check not authorized user can change name")
    public void notAuthUserIsChangingName(ValidatableResponse changingResponseName, String newName) {
        changingResponseName
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Check not authorized user can change email")
    public void notAuthUserIsChangingEmail(ValidatableResponse changingResponseEmail, String newEmail) {
        changingResponseEmail
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Check not authorized user can change password")
    public void notAuthUserIsChangingPassword(ValidatableResponse changingResponsePassword, String newPassword) {
        changingResponsePassword
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("User is deleted")
    public void deletedSuccesfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED);
    }

    public String extractToken(ValidatableResponse response) {
        return response.extract().jsonPath().getString("accessToken");
    }

    }