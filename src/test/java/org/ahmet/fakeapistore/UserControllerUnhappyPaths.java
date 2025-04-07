package org.ahmet.fakeapistore;

    import io.restassured.RestAssured;
    import io.restassured.http.ContentType;
    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Test;

    import static io.restassured.RestAssured.given;
    import static org.hamcrest.Matchers.equalTo;

    public class UserControllerUnhappyPaths {

        private static final String BASE_URI = "https://fakestoreapi.com";
        private static final String USERS_ENDPOINT = "/users";

        @BeforeAll
        public static void setup() {
            RestAssured.baseURI = BASE_URI;
        }

        @Test
        public void shouldReturnErrorForInvalidUserIdInGetUser() {
            given()
                .pathParam("id", "invalid-id")
                .when()
                .get(USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("user id should be provided"));
        }

        @Test
        public void shouldReturnErrorForUndefinedBodyInAddUser() {
            given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post(USERS_ENDPOINT)
                .then()
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
                .put(USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("something went wrong! check your sent data"));
        }

        @Test
        public void shouldReturnErrorForNullIdInDeleteUser() {
            given()
                .pathParam("id", "null")
                .when()
                .delete(USERS_ENDPOINT + "/{id}")
                .then()
                .statusCode(400)
                .body("message", equalTo("user id should be provided"));
        }
    }