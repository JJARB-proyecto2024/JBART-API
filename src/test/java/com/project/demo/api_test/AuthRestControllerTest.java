package com.project.demo.api_test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthRestControllerTest {

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/auth";
    }

    @Test
    void login_Success() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"email\": \"mg@gmail.com\", \"password\": \"manuel123\" }"
                )
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("token", notNullValue())
                .body("authUser", notNullValue())
                .body("authUser.email", equalTo("mg@gmail.com"));
    }

    @Test
    void login_Invalid_Credentials() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"email\": \"mg@gmail.com\", \"password\": \"wrong123\" }"
                )
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void login_Empty_Credentials() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"email\": \"\", \"password\": \"\" }"
                )
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    void generate_Password_Reset_Otp() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"email\": \"mg@gmail.com\" }"
                )
                .when()
                .post("/generatePasswordResetOtp")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("OTP generado exitosamente"));
    }



    @Test
    void generate_Password_Reset_UserNotFound() {
        given()
                .contentType(ContentType.JSON)
                .body(
                        "{ \"email\": \"ka@gmail.com\" }"
                )
                .when()
                .post("/generatePasswordResetOtp")
                .then()
                .statusCode(anyOf(is(HttpStatus.OK.value()),
                        is(HttpStatus.NOT_FOUND.value())));
    }


}
