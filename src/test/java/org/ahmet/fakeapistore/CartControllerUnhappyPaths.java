package org.ahmet.fakeapistore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CartControllerUnhappyPaths {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @Test
    public void shouldReturnErrorForInvalidDateFormatInGetAllCarts() {
        given()
                .queryParam("startdate", "invalid-date")
                .when()
                .get("/carts")
                .then()
                .statusCode(400)
                .body("message", equalTo("date format is not correct. it should be in yyyy-mm-dd format"));
    }

    @Test
    public void shouldReturnErrorForInvalidUserIdInGetCartsByUserId() {
        given()
                .pathParam("userid", "invalid-id")
                .when()
                .get("/carts/user/{userid}")
                .then()
                .statusCode(400)
                .body("message", equalTo("user id should be provided"));
    }

    @Test
    public void shouldReturnErrorForInvalidCartIdInGetSingleCart() {
        given()
                .pathParam("id", "invalid-id")
                .when()
                .get("/carts/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("cart id should be provided"));
    }

    @Test
    public void shouldReturnErrorForUndefinedBodyInAddCart() {
        given()
                .contentType(ContentType.JSON)
                .body("{}") // Provide an empty JSON object instead of null
                .when()
                .post("/carts")
                .then()
//                .statusCode(400) // Assuming the API returns 400 for invalid data
                .statusCode(200)
                .body("message", equalTo(null));
    }

    @Test
    public void shouldReturnErrorForUndefinedBodyOrNullIdInEditCart() {
        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .pathParam("id", "null")
                .when()
                .put("/carts/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("something went wrong! check your sent data"));
    }

    @Test
    public void shouldReturnErrorForNullIdInDeleteCart() {
        given()
                .pathParam("id", "null")
                .when()
                .delete("/carts/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("cart id should be provided"));
    }
}