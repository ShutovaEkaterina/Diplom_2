package userpackage;

import basicpackage.BasicStaff;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

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
}