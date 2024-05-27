package ordertests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orderpackage.Order;
import orderpackage.OrderPath;
import orderpackage.OrderResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import userpackage.User;
import userpackage.UserPath;
import userpackage.UserResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class OrderTest {
    private final OrderPath orderPath = new OrderPath();
    private final OrderResponse orderResponse = new OrderResponse(0);
    static String accessToken;
    static String hash_ingredient_1 = "60d3b41abdacab0026a733c6";
    static String hash_ingredient_2 = "609646e4dc916e00276b2870";
    static String hash_ingredient_3 = "61c0c5a71d1f82001bdaaa6d";
    static String hash_ingredient_4 = "61c0c5a71d1f82001bdaaa72";

    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();

    @Before
    public void setUp() {
        User user = User.random();

        ValidatableResponse createResponse = userPath.createUser(user);

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

    // Тест упадет, потому что, согласно документации, должен возврашаться статус код 500, а верунлся 400
    @Test
    @DisplayName("Creating order with auth and incorrect ingredient")
    public void creatingOrderWithAuthIncorrectIngredient() {
        List<String> ingredients = Arrays.asList(hash_ingredient_1, hash_ingredient_2);
        Order order = new Order(ingredients);

        ValidatableResponse orderResponseAuthIncIngredient = orderPath.createOrderAuthIncorrectIngredient(accessToken, order);

        orderResponse.orderCreatingWithAuthIncorrectIngredient(orderResponseAuthIncIngredient);
    }
    @Test
    @DisplayName("Creating order with auth and correct ingredient")
    public void creatingOrderWithAuthCorrectIngredient() {

        List<String> ingredients = Arrays.asList(hash_ingredient_3, hash_ingredient_4);
        Order order = new Order(ingredients);

        ValidatableResponse orderResponseAuthCorIngredient = orderPath.createOrderAuthCorrectIngredient(accessToken, order);

        orderResponse.orderCreatingWithAuthCorrectIngredient(orderResponseAuthCorIngredient);
    }
    @Test
    @DisplayName("Creating order with auth and without ingredient")
    public void creatingOrderWithAuthWithoutIngredient() {

        List<String> ingredients = new ArrayList<>();
        Order order = new Order(ingredients);

        ValidatableResponse orderResponseAuthWithoutIngredient = orderPath.createOrderAuthWithoutIngredient(accessToken, order);

        orderResponse.orderCreatingWithAuthWithoutIngredient(orderResponseAuthWithoutIngredient);
    }
    @Test
    @DisplayName("Creating order not auth and with ingredient")
    public void creatingOrderNotAuthWithIngredient() {

        List<String> ingredients = Arrays.asList(hash_ingredient_3, hash_ingredient_4);
        Order order = new Order(ingredients);

        ValidatableResponse orderResponseNotAuthWithIngredient = orderPath.createOrderNotAuthWithIngredient(order);

        orderResponse.orderCreatingNotAuthWithIngredient(orderResponseNotAuthWithIngredient);
    }

}
