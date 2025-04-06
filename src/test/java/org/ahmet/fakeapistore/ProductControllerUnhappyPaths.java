package org.ahmet.fakeapistore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductControllerUnhappyPaths {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

//    @Test
//    public void shouldReturnErrorForInvalidProductIdInGetProduct() {
//        given()
//            .pathParam("id", "invalid-id")
//        .when()
//            .get("/products/{id}")
//        .then()
//            .statusCode(400)
//            .body("message", equalTo("Invalid product ID"));
//    }
//
//    @Test
//    public void shouldReturnErrorForUndefinedBodyInAddProduct() {
//        given()
//            .contentType(ContentType.JSON)
//            .body("{}") // Provide an empty JSON object instead of null
//        .when()
//            .post("/products")
//        .then()
//            .statusCode(400)
//            .body("message", equalTo("data is undefined"));
//    }

    @Test
    public void shouldReturnErrorForUndefinedBodyOrNullIdInEditProduct() {
        given()
            .contentType(ContentType.JSON)
            .body("{}")
            .pathParam("id", "null")
        .when()
            .put("/products/{id}")
        .then()
            .statusCode(400)
            .body("message", equalTo("something went wrong! check your sent data"));
    }

    @Test
    public void shouldReturnErrorForNullIdInDeleteProduct() {
        given()
            .pathParam("id", "null")
        .when()
            .delete("/products/{id}")
        .then()
            .statusCode(400)
            .body("message", equalTo("product id should be provided"));
    }
}