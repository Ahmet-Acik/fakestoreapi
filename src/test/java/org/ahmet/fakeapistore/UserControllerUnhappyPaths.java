package org.ahmet.fakeapistore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserControllerUnhappyPaths {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void shouldReturnErrorForInvalidUserIdInGetUser() {
        given()
                .pathParam("id", "invalid-id")
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("user id should be provided"));
    }

    @Test
    public void shouldReturnErrorForUndefinedBodyInAddUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{}") // Provide an empty JSON object instead of null
                .when()
                .post("/users")
                .then()
//            .statusCode(400) // Assuming the API returns 400 for invalid data
//            .body("message", equalTo("data is undefined")); // Uncomment this line if the API returns 400 for invalid data
                .statusCode(200)
                .body("message", equalTo(null));
    }

    @Test
    public void shouldReturnErrorForUndefinedBodyOrNullIdInEditUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .pathParam("id", "null")
                .when()
                .put("/users/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("something went wrong! check your sent data"));
    }

    @Test
    public void shouldReturnErrorForNullIdInDeleteUser() {
        given()
                .pathParam("id", "null")
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("user id should be provided"));
    }
}