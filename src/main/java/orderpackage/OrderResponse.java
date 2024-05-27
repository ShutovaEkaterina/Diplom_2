package orderpackage;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderResponse {
    private String orderNumber;

    @Step("Check order is creating with auth and incorrect ingredient")
    public void orderCreatingWithAuthIncorrectIngredient(ValidatableResponse orderResponseAuthIncIngredient) {
        orderResponseAuthIncIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR); // Проверка, что статус ответа 200 OK
    }

    @Step("Check order is creating with auth and correct ingredient")
    public void orderCreatingWithAuthCorrectIngredient(ValidatableResponse orderResponseAuthCorIngredient) {
        orderResponseAuthCorIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK) // Проверка, что статус ответа 200 OK
                .body("success", equalTo(true)) // Проверка, что поле success равно true
                .body("order.number", notNullValue()); // Проверка, что поле number не пустое

// Сохранение значения поля number
        orderNumber = orderResponseAuthCorIngredient.extract().path("order.number").toString();
    }

    @Step("Check order is creating with auth and without ingredient")
    public void orderCreatingWithAuthWithoutIngredient(ValidatableResponse orderResponseAuthWithoutIngredient) {
        orderResponseAuthWithoutIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST) // Проверка, что статус ответа 200 OK
                .body("success", equalTo(false)) // Проверка, что поле success равно true
                .body("message", equalTo("Ingredient ids must be provided")); // Проверка, что поле number не пустое
    }
    @Step("Check order is creating not auth and with ingredient")
    public void orderCreatingNotAuthWithIngredient(ValidatableResponse orderResponseNotAuthWithIngredient) {
        orderResponseNotAuthWithIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK) // Проверка, что статус ответа 200 OK
                .body("success", equalTo(true)) // Проверка, что поле success равно true
                .body("order.number", notNullValue()); // Проверка, что поле number не пустое

// Сохранение значения поля number
        orderNumber = orderResponseNotAuthWithIngredient.extract().path("order.number").toString();
    }
}
