import api.CreateUser;
import api.DeleteUser;
import api.LoginUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.Data;
import testData.DataSerialization;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserTest extends Data {
    private final CreateUser createUser = new CreateUser();
    private final DeleteUser deleteUser = new DeleteUser();
    private final LoginUser loginUser = new LoginUser();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginUserTest() {
        DataSerialization data = new DataSerialization(email, password, null);
        Response userCreateResponse = createUser.createUser(userCreateData);
        Response loginUserResponse = loginUser.loginUser(data);
        loginUserResponse.then().log().all().statusCode(200).assertThat().body("success", equalTo(true));
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginUserIncorrectPasswordTest() {
        DataSerialization data = new DataSerialization(email, password + 12, null);
        Response userCreateResponse = createUser.createUser(userCreateData);
        Response loginUserResponse = loginUser.loginUser(data);
        loginUserResponse.then().log().all().statusCode(401).assertThat().body("message", equalTo("email or password are incorrect"));
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Логин с неверным login")
    public void loginUserIncorrectLoginTest() {
        DataSerialization data = new DataSerialization(email + "s", password, null);
        Response userCreateResponse = createUser.createUser(userCreateData);
        Response loginUserResponse = loginUser.loginUser(data);
        loginUserResponse.then().log().all().statusCode(401).assertThat().body("message", equalTo("email or password are incorrect"));
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            deleteUser.deleteUser(accessToken);
        }
    }

}
