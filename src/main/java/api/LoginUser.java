package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import testData.Data;

import static io.restassured.RestAssured.given;

public class LoginUser extends Data {
    @Step("авторизация пользователя")
    public Response loginUser(Object body) {
        return given()
                .header(headersRequestContentType, headersRequestApplication)
                .and()
                .body(body)
                .when().log().all()
                .post(loginUserApi);


    }

}
