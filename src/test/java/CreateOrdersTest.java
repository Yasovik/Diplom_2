import api.CreateOrdersApi;
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
import testData.DataSerializationOrders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrdersTest extends Data {
    private final CreateUser createUser = new CreateUser();
    private final DeleteUser deleteUser = new DeleteUser();
    private final LoginUser loginUser = new LoginUser();
    private final CreateOrdersApi createOrdersApi = new CreateOrdersApi();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        Response userCreateResponse = createUser.createUser(userCreateData);
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Создание заказа c авторизацией")
    public void createOrderWithAuthorizationTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
        Response loginUserResponse = loginUser.loginUser(userCreateData);
        loginUserResponse.then().statusCode(200);
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(200).assertThat().body("order.number", notNullValue());

    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
        Response createOrders = createOrdersApi.CreateNewOrders(data, "");
        createOrders.then().log().all().statusCode(401).assertThat().body("message", equalTo("You should be authorised"));

    }

    @Test
    @DisplayName("Создание заказа c ингредиентами")
    public void createOrderWithIngredientTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
        Response loginUserResponse = loginUser.loginUser(userCreateData);
        loginUserResponse.then().statusCode(200);
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(200).assertThat().body("order.number", notNullValue());

    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredientTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{});
        Response loginUserResponse = loginUser.loginUser(userCreateData);
        loginUserResponse.then().statusCode(200);
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(400).assertThat().body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    @DisplayName("Создание заказа c неверным хешем")
    public void createOrderWithInvalidHashTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6", "61c0c5a71d1f82001bdaaa70"});
        Response loginUserResponse = loginUser.loginUser(userCreateData);
        loginUserResponse.then().statusCode(200);
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(500);

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            deleteUser.deleteUser(accessToken);
        }

    }

}
