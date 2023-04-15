package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import testData.Data;

import static io.restassured.RestAssured.given;

public class CreateOrdersApi extends Data {
    @Step("Создание нового заказа")
    public Response CreateNewOrders(Object body, String accessToken) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when().log().all()
                .post(createOrdersApi);
    }

    @Step("Получение списка заказов")
    public Response getOrders(Object body, String accessToken) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when().log().all()
                .get(createOrdersApi);
    }

}
