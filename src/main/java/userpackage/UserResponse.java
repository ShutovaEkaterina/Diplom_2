package userpackage;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import static org.junit.Assert.*;
public class UserResponse {
    @Step("Check user can login as an existing")
    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id")
                ;
        return id;
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

    }