package api;

import io.qameta.allure.Step;
import testData.Data;

import static io.restassured.RestAssured.given;


public class DeleteUser extends Data {

    @Step("Удалить пользователя")
    public void deleteUser(String accessToken) {
        given()
                .auth().oauth2(accessToken)
                .header(headersRequestContentType, headersRequestApplication)
                .body(userCreateData)
                .when()
                .delete(deleteUserApi).then().statusCode(202).log().all();
    }

}

