package org.ahmet.junitpractices;

import io.restassured.http.ContentType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class CsvSourceRestAssuredTest {

    @ParameterizedTest
    @CsvSource({"NY, New York", "VA, Fairfax", "MA, Boston"})
    public void testCityAPI(String state, String city) {
        given()
                .baseUri("https://api.zippopotam.us")
                .accept(ContentType.JSON)
                .pathParams("state", state, "city", city)
        .when()
                .get("/us/{state}/{city}")
        .then()
                .statusCode(200)
//                .body("places.'place name'", containsString(city));
                .body("places.'place name'", hasItem(containsString(city)));
    }
}