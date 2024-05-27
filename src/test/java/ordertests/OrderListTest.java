package ordertests;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orderpackage.OrderPath;
import orderpackage.OrderResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import userpackage.User;
import userpackage.UserPath;
import userpackage.UserResponse;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    private final OrderPath orderPath = new OrderPath();
    private final OrderResponse orderResponse = new OrderResponse(0);
    static String accessToken;
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
    //тест упадет, потому что общее количество заказов у только что созданного юзера = 0
    @Test
    @DisplayName("Getting all orders for auth user")
    public void gettingAllOrdersForAuthUser() {
        int expectedTotalToday = 0;

        ValidatableResponse checkResponseOrdersAuthUser = orderPath.getOrdersAuthUser(accessToken);

        orderResponse.checkAllOrdersAuthUser(checkResponseOrdersAuthUser, expectedTotalToday);
    }
    @Test
    @DisplayName("Getting all orders for not auth user")
    public void gettingAllOrdersForNotAuthUser() {
        int expectedTotalToday = 0;

        ValidatableResponse checkResponseOrdersNotAuthUser = orderPath.getOrdersNotAuthUser();

        orderResponse.checkAllOrdersNotAuthUser(checkResponseOrdersNotAuthUser, expectedTotalToday);
    }
}
