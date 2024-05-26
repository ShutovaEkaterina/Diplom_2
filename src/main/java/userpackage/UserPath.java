package userpackage;

import basicpackage.BasicStaff;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.when;

public class UserPath extends BasicStaff {
    private static final String USER_PATH = "/auth";

    @Step("Login existing user")
    public ValidatableResponse loginUser(UserCreds creds) {
        return spec()
                .body(creds)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("Create unique user")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then().log().all();
    }

    @Step("Create existing user")
    public ValidatableResponse createExistingUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then().log().all();
    }

    @Step("Create user without obligatory field")
    public ValidatableResponse createUserWithoutField(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then().log().all();
    }

    @Step("Courier login with incorrect email and password")
    public ValidatableResponse loginIncorrectEmailPassword(UserCreds user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("User is deleted")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("Auth user is changing name")
    public ValidatableResponse changingName(String accessToken, String name) {
        Map<String, String> updatedUserData = Map.of("name", name);
        return spec()
                .header("Authorization", accessToken)
                .body(updatedUserData)
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }
    @Step("Auth user is changing email")
    public ValidatableResponse changingEmail(String accessToken, String email) {
        Map<String, String> updatedUserData = Map.of("email", email);
        return spec()
                .header("Authorization", accessToken)
                .body(updatedUserData)
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }
    @Step("Auth user is changing password")
    public ValidatableResponse changingPassword(String accessToken, String password) {
        Map<String, String> updatedUserData = Map.of("password", password);
        return spec()
                .header("Authorization", accessToken)
                .body(updatedUserData)
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }
    @Step("Not auth user is changing name")
    public ValidatableResponse changingNameNotAuth(String name) {
        Map<String, String> updatedUserData = Map.of("name", name);
        return spec()
                .body(updatedUserData)
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }
}