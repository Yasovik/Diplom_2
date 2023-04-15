import api.CreateUser;
import api.DeleteUser;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testData.Data;
import testData.DataSerialization;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserTest extends Data {
    private final CreateUser createUser = new CreateUser();
    private final DeleteUser deleteUser = new DeleteUser();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Создание нового Пользователя")
    public void createNewUserTest() {
        Response userCreateResponse = createUser.createUser(userCreateData);
        userCreateResponse.then().log().all().statusCode(200).assertThat().body("success", equalTo(true));
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Создание пользователя который уже зарегистрирован")
    public void CreatedDoubleUserTest() {
        Response userCreateResponse = createUser.createUser(userCreateData);
        Response userCreateDoubleResponse = createUser.createUser(userCreateData);
        userCreateDoubleResponse.then().log().all().statusCode(403).assertThat().body("message", equalTo("User already exists"));
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    public void CreateUserWithoutPassword() {
        DataSerialization data = new DataSerialization(email, "", name);
        Response userCreateResponse = createUser.createUser(data);
        userCreateResponse.then().log().all().statusCode(403).assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            deleteUser.deleteUser(accessToken);
        }
    }

}
