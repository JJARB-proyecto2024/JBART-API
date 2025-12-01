package com.project.demo.helpers;


import com.project.demo.logic.entity.user.LoginResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class AuthTestHelper {

    private static final String AUTH_PATH = "/auth";
    private static final String LOGIN_ENDPOINT = "/login";

    /**
     * Obtiene un token JWT para un usuario específico
     */
    public static String getAuthToken(String email, String password) {
        Response response = given()
                .contentType(ContentType.JSON)
                .basePath(AUTH_PATH)
                .body(String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password))
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.jsonPath().getString("token");
    }

    /**
     * Token para usuario regular
     */
    public static String getUserToken() {
        return getAuthToken("mg@gmail.com", "manuel123");
    }

    /**
     * Token para usuario brand
     */
    public static String getBrandToken() {
        return getAuthToken("guess@gmail.com", "guess123");
    }

    /**
     * Token para super admin
     */
    public static String getAdminToken() {
        return getAuthToken("admin@example.com", "admin123");
    }

}
