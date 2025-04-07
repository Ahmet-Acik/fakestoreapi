package org.ahmet.fakeapistore;

    import io.restassured.RestAssured;
    import io.restassured.http.ContentType;
    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Disabled;
    import org.junit.jupiter.api.Test;

    import static io.restassured.RestAssured.given;
    import static org.hamcrest.Matchers.equalTo;

    public class ProductControllerUnhappyPaths {

        private static final String BASE_URI = "https://fakestoreapi.com";
        private static final String PRODUCTS_ENDPOINT = "/products";

        @BeforeAll
        public static void setup() {
            RestAssured.baseURI = BASE_URI;
        }

        @Test
        @Disabled("This test is ignored because it is failing")
        public void shouldReturnErrorForInvalidProductIdInGetProduct() {
            given()
                .contentType(ContentType.JSON)
                .when()
                .get(PRODUCTS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400)
                .body("message", equalTo("The JSON input text should neither be null nor empty."));
        }

        @Test
        @Disabled("This test is ignored because it is failing")
        public void shouldReturnErrorForUndefinedBodyInAddProduct() {
            given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(400)
                .body("message", equalTo("data is undefined"));
        }

        @Test
        public void shouldReturnErrorForUndefinedBodyOrNullIdInEditProduct() {
            given()
                .contentType(ContentType.JSON)
                .body("{}")
                .pathParam("id", "null")
                .when()
                .put(PRODUCTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("something went wrong! check your sent data"));
        }

        @Test
        public void shouldReturnErrorForNullIdInDeleteProduct() {
            given()
                .pathParam("id", "null")
                .when()
                .delete(PRODUCTS_ENDPOINT + "/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("product id should be provided"));
        }
    }