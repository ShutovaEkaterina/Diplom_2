package orderpackage;

import basicpackage.BasicStaff;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import userpackage.UserCreds;

public class OrderPath extends BasicStaff {
    private static final String ORDER_PATH = "/orders";

    @Step("Create order with auth and incorrect ingredient")
    public ValidatableResponse createOrderAuthIncorrectIngredient(String accessToken, Order order) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("Create order with auth and correct ingredient")
    public ValidatableResponse createOrderAuthCorrectIngredient(String accessToken, Order order) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }
    @Step("Create order with auth and without ingredient")
    public ValidatableResponse createOrderAuthWithoutIngredient(String accessToken, Order order) {
        return spec()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }
    @Step("Create order not auth and with ingredient")
    public ValidatableResponse createOrderNotAuthWithIngredient(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }
    @Step("Get orders auth user")
    public ValidatableResponse getOrdersAuthUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
    @Step("Get orders not auth user")
    public ValidatableResponse getOrdersNotAuthUser() {
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}
