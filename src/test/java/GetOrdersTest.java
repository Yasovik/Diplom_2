import api.CreateOrdersApi;
import api.CreateUser;
import api.DeleteUser;
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

public class GetOrdersTest extends Data {
    private final CreateUser createUser = new CreateUser();
    private final DeleteUser deleteUser = new DeleteUser();
    private final CreateOrdersApi createOrdersApi = new CreateOrdersApi();
    private String accessToken;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        Response userCreateResponse = createUser.createUser(userCreateData);
        accessToken = userCreateResponse.then().extract().path("accessToken").toString().substring(6).trim();
    }

    @Test
    @DisplayName("Получение заказа конкретного пользователя с авторизацией")
    public void getOrderWithAuthorizationTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(200).assertThat().body("order.number", notNullValue());
        Response getOrders = createOrdersApi.getOrders(userCreateData, accessToken);
        getOrders.then().log().all().statusCode(200).assertThat().body("success", equalTo(true));

    }

    @Test
    @DisplayName("Получение заказа конкретного пользователя без авторизацией")
    public void getOrderWithoutAuthorizationTest() {
        DataSerializationOrders data = new DataSerializationOrders(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70"});
        Response createOrders = createOrdersApi.CreateNewOrders(data, accessToken);
        createOrders.then().log().all().statusCode(200).assertThat().body("order.number", notNullValue());
        Response getOrders = createOrdersApi.getOrders(userCreateData, "");
        getOrders.then().log().all().statusCode(401).assertThat().body("message", equalTo("You should be authorised"));

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            deleteUser.deleteUser(accessToken);
        }
    }

}
