package org.ahmet.fakeapistore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FakeStoreApiCrudTests {

    private static final String BASE_URI = "https://fakestoreapi.com";
    private static final String PRODUCTS_ENDPOINT = "/products";
    private static final String USERS_ENDPOINT = "/users";
    private static final String CARTS_ENDPOINT = "/carts";
    private static final String AUTH_ENDPOINT = "/auth/login";

    private static int createdProductId;
    private static int createdUserId;
    private static int createdCartId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    // PRODUCTS TESTS
    @Test
    @Order(1)
    public void testCreateProduct_HappyPath() {
        String payload = """
                {
                    "title": "Test Product",
                    "price": 99.99,
                    "description": "A test product",
                    "image": "https://i.pravatar.cc",
                    "category": "electronics"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(PRODUCTS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        createdProductId = response.path("id");
        assertThat((String) response.path("title")).isEqualTo("Test Product");
    }

    @Test
    @Order(2)
    public void testGetProductById_HappyPath() {
        given()
                .get(PRODUCTS_ENDPOINT + "/" + createdProductId)
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    public void testUpdateProduct_HappyPath() {
        String updatedPayload = """
                {
                    "title": "Updated Test Product",
                    "price": 109.99,
                    "description": "An updated test product",
                    "image": "https://i.pravatar.cc",
                    "category": "electronics"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updatedPayload)
                .put(PRODUCTS_ENDPOINT + "/" + createdProductId)
                .then()
                .statusCode(200)
                .extract().response();

        assertThat((String) response.path("title")).isEqualTo("Updated Test Product");
    }

    @Test
    public void testDeleteProduct_UnhappyPath_BadRequest() {
        given()
                .delete(PRODUCTS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(4)
    public void testDeleteProduct_HappyPath() {
        given()
                .delete(PRODUCTS_ENDPOINT + "/" + createdProductId)
                .then()
                .statusCode(200);
    }

    // USERS TESTS
    @Test
    @Order(5)
    public void testCreateUser_HappyPath() {
        String payload = """
                {
                    "email": "testuser@example.com",
                    "username": "testuser",
                    "password": "password123",
                    "name": { "firstname": "John", "lastname": "Doe" },
                    "address": { "city": "City", "street": "Street", "number": 1, "zipcode": "12345" },
                    "phone": "123-456-7890"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(USERS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        createdUserId = response.path("id");
        assertThat(createdUserId).isPositive();
    }

    @Test
    @Order(6)
    public void testGetUserById_HappyPath() {
        given()
                .get(USERS_ENDPOINT + "/" + createdUserId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUserById_UnhappyPath_BadRequest() {
        given()
                .get(USERS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(7)
    public void testUpdateUser_HappyPath() {
        String payload = """
                {
                    "username": "updateduser",
                    "password": "newpass123"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .put(USERS_ENDPOINT + "/" + createdUserId)
                .then()
                .statusCode(200)
                .extract().response();

        assertThat((String) response.path("username")).isEqualTo("updateduser");
    }

    @Test
    public void testDeleteUser_UnhappyPath_BadRequest() {
        given()
                .delete(USERS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(8)
    public void testDeleteUser_HappyPath() {
        given()
                .delete(USERS_ENDPOINT + "/" + createdUserId)
                .then()
                .statusCode(200);
    }

    // CARTS TESTS
    @Test
    @Order(9)
    public void testCreateCart_HappyPath() {
        String payload = """
                {
                    "userId": 1,
                    "date": "2023-01-01",
                    "products": [{"productId": 1, "quantity": 1}]
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(CARTS_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        createdCartId = response.path("id");
        assertThat(createdCartId).isPositive();
    }

    @Test
    @Order(10)
    public void testGetCartById_HappyPath() {
        given()
                .get(CARTS_ENDPOINT + "/" + createdCartId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCartById_UnhappyPath_BadRequest() {
        given()
                .get(CARTS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(11)
    public void testUpdateCart_HappyPath() {
        String payload = """
                {
                    "userId": 1,
                    "date": "2023-01-02",
                    "products": [{"productId": 2, "quantity": 2}]
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .put(CARTS_ENDPOINT + "/" + createdCartId)
                .then()
                .statusCode(200)
                .extract().response();

        assertThat((Integer) response.path("products[0].quantity")).isEqualTo(2);
    }

    @Test
    public void testDeleteCart_UnhappyPath_BadRequest() {
        given()
                .delete(CARTS_ENDPOINT + "/invalid-id")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(12)
    public void testDeleteCart_HappyPath() {
        given()
                .delete(CARTS_ENDPOINT + "/" + createdCartId)
                .then()
                .statusCode(200);
    }

    // AUTH TEST
    @Test
    public void testLogin_HappyPath() {
        String payload = """
                {
                    "username": "mor_2314",
                    "password": "83r5^_"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();

        String token = response.path("token");
        assertThat(token).isNotBlank();
    }

    @Test
    public void testLogin_UnhappyPath_InvalidCredentials() {
        String payload = """
                {
                    "username": "mor_2314",
                    "password": "wrongpass"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(401);
    }

    @Test
    public void testLogin_UnhappyPath_MissingUsername_1() {
        String payload = """
                {
                    "password": "83r5^_"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    public void testLogin_UnhappyPath_MissingPassword_1() {
        String payload = """
                {
                    "username": "mor_2314"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    public void testLogin_UnhappyPath_EmptyUsername() {
        String payload = """
                {
                    "username": "",
                    "password": "83r5^_"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post(AUTH_ENDPOINT)
                .then()
                .statusCode(400);
    }
}