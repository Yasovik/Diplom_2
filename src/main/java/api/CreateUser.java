package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import testData.Data;

import static io.restassured.RestAssured.given;

public class CreateUser extends Data {

    @Step("Создание нового пользователя")
    public Response createUser(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .post(apiCreateUser);


    }

}
