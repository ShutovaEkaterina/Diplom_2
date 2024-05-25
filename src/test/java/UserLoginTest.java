import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import userpackage.UserCreds;
import userpackage.UserPath;
import userpackage.UserResponse;

import static org.junit.Assert.assertNotEquals;

public class UserLoginTest {
    private final UserPath userPath = new UserPath();
    private final UserResponse userResponse = new UserResponse();
    //int courierId;

    //@After
    //public void deleteCourier() {
        //if (courierId != 0) {
            //ValidatableResponse response = courierPath.deleteCourier(courierId);
            //courierIsLogged.deletedSuccesfully(response);
        //}
    //}

    @DisplayName("Existing user can login")
    @Test
    public void userLoginSuccess() {

        UserTest userTest = new UserTest();
        userTest.user();

        UserCreds creds = UserCreds.from(UserTest.user);
        ValidatableResponse loginResponse = userPath.loginUser(creds);
        accessToken = userResponse.loggedInSuccessfully(loginResponse);

        assertNotEquals(0, accessToken);
    }


}
