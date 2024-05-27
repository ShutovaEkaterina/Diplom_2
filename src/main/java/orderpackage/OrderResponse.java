package orderpackage;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;
import java.util.Collections;
import static org.hamcrest.Matchers.*;

public class OrderResponse {
    private final int expectedTotalToday;
    public OrderResponse(int expectedTotalToday) {
        this.expectedTotalToday = expectedTotalToday;
    }

    @Step("Check order is creating with auth and incorrect ingredient")
    public void orderCreatingWithAuthIncorrectIngredient(ValidatableResponse orderResponseAuthIncIngredient) {
        orderResponseAuthIncIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Check order is creating with auth and correct ingredient")
    public void orderCreatingWithAuthCorrectIngredient(ValidatableResponse orderResponseAuthCorIngredient) {
        orderResponseAuthCorIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Step("Check order is creating with auth and without ingredient")
    public void orderCreatingWithAuthWithoutIngredient(ValidatableResponse orderResponseAuthWithoutIngredient) {
        orderResponseAuthWithoutIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Check order is creating not auth and with ingredient")
    public void orderCreatingNotAuthWithIngredient(ValidatableResponse orderResponseNotAuthWithIngredient) {
        orderResponseNotAuthWithIngredient
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Step("Check all orders auth user")
    public void checkAllOrdersAuthUser(ValidatableResponse checkResponseOrdersAuthUser, int expectedTotalToday) {
        checkResponseOrdersAuthUser
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true));
        if (checkResponseOrdersAuthUser.extract().path("orders") == null ||
                checkResponseOrdersAuthUser.extract().path("orders").equals(Collections.emptyList())) {
            checkResponseOrdersAuthUser
                    .body("total", equalTo(0))
                    .body("totalToday", equalTo(0));
        }  else {
            int actualTotal = checkResponseOrdersAuthUser.extract().path("orders.size()");

            checkResponseOrdersAuthUser
                    .body("total", equalTo(actualTotal))
                    .body("totalToday", equalTo(this.expectedTotalToday));
        }
    }
    @Step("Check all orders not auth user")
    public void checkAllOrdersNotAuthUser(ValidatableResponse checkResponseOrdersNotAuthUser, int expectedTotalToday) {
        checkResponseOrdersNotAuthUser
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
