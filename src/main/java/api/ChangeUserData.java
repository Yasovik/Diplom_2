package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import testData.Data;

import static io.restassured.RestAssured.given;

public class ChangeUserData extends Data {

    @Step("Изменение данных пользователя")
    public Response patchChangeUserData(Object body, String accessToken) {
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .and()
                .body(body)
                .when().log().all()
                .patch(deleteUserApi);
    }

}
