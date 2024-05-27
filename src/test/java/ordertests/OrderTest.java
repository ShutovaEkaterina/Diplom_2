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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;


public class OrderTest {
    private final OrderPath orderPath = new OrderPath();
    private final OrderResponse orderResponse = new OrderResponse();
    static String accessToken;
    static String hash_ingredient_1 = "60d3b41abdacab0026a733c6";
    static String hash_ingredient_2 = "609646e4dc916e00276b2870";
    static String hash_ingredient_3 = "61c0c5a71d1f82001bdaaa6d";
    static String hash_ingredient_4 = "61c0c5a71d1f82001bdaaa72";

    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();

    @Before
    public void setUp() {
        // Создание нового пользователя перед каждым тестом
        User user = User.random();

        ValidatableResponse createResponse = userPath.createUser(user);

        // Получение токена доступа из ответа на создание пользователя

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

    // Тест упадет, потому что какой-то из ингредиентов некорректный
    @Test
    @DisplayName("Creating order with auth and incorrect ingredient")
    public void creatingOrderWithAuthIncorrectIngredient() {
        // Создание и инициализация объекта Order
        List<String> ingredients = Arrays.asList(hash_ingredient_1, hash_ingredient_2);
        Order order = new Order(ingredients);

        // Вызов метода createOrderAuth с передачей accessToken и объекта Order
        ValidatableResponse orderResponseAuthIncIngredient = orderPath.createOrderAuthIncorrectIngredient(accessToken, order);

        // Проверка ответа на запрос создания заказа
        orderResponse.orderCreatingWithAuthIncorrectIngredient(orderResponseAuthIncIngredient);
    }
    @Test
    @DisplayName("Creating order with auth and correct ingredient")
    public void creatingOrderWithAuthCorrectIngredient() {
        // Создание и инициализация объекта Order
        List<String> ingredients = Arrays.asList(hash_ingredient_3, hash_ingredient_4);
        Order order = new Order(ingredients);

        // Вызов метода createOrderAuth с передачей accessToken и объекта Order
        ValidatableResponse orderResponseAuthCorIngredient = orderPath.createOrderAuthCorrectIngredient(accessToken, order);

        // Проверка ответа на запрос создания заказа
        orderResponse.orderCreatingWithAuthCorrectIngredient(orderResponseAuthCorIngredient);
    }
}
