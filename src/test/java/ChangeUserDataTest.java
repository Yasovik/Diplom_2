import api.ChangeUserData;
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

public class ChangeUserDataTest extends Data {
    private final CreateUser createUser = new CreateUser();
    private final DeleteUser deleteUser = new DeleteUser();
    private final LoginUser loginUser = new LoginUser();
    private final ChangeUserData changeUserData = new ChangeUserData();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        Response userCreateResponse = createUser.createUser(userCreateData);
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeUserDataWithAuthorization() {
        DataSerialization data = new DataSerialization(email, password, name);
        DataSerialization newData = new DataSerialization("s" + email, password + 6, name + "a");
        Response loginUserResponse = loginUser.loginUser(data);
        loginUserResponse.then().statusCode(200);
        Response changeUserDataResponse = changeUserData.patchChangeUserData(newData, accessToken);
        changeUserDataResponse.then().log().all().statusCode(200).assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void changeUserDataWithoutAuthorization() {
        DataSerialization data = new DataSerialization(email, password, name);
        DataSerialization newData = new DataSerialization("s" + email, password + 6, name + "a");
        Response loginUserResponse = loginUser.loginUser(data);
        loginUserResponse.then().statusCode(200);
        Response changeUserDataResponse = changeUserData.patchChangeUserData(newData, "");
        changeUserDataResponse.then().log().all().statusCode(401).assertThat().body("message", equalTo("You should be authorised"));
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            deleteUser.deleteUser(accessToken);
        }
    }

}
