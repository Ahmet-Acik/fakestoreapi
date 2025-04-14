package org.ahmet.junitpractices;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class RestAssuredParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {22030, 22031, 22032})
    public void testZipCodeAPI(int zipCode) {
        RestAssured.baseURI = "https://api.zippopotam.us";

        given()
                .accept(ContentType.JSON)
                .pathParam("zipCode", zipCode)
        .when()
                .get("/us/{zipCode}")
        .then()
                .statusCode(200);
    }
}