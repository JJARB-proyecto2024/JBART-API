package com.project.demo.api_test;

import com.project.demo.helpers.AuthTestHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/usersBuyer";
    }


    @Test
    void getUserByName() {
        given()
                .header("Authorization", "Bearer " + AuthTestHelper.getUserToken())
                .when()
                .get("/filterByName/{name}", "Ashley")
                .then()
                .statusCode(HttpStatus.OK.value());

    }


    @Test
    void getUserWithEmptyName() {
        given()
                .header("Authorization", "Bearer " + AuthTestHelper.getUserToken())
                .when()
                .get("/filterByName/{name}", "")
                .then()
                .statusCode(anyOf(
                        is(HttpStatus.NOT_FOUND.value()),
                        is(HttpStatus.BAD_REQUEST.value()),
                        is(HttpStatus.INTERNAL_SERVER_ERROR.value())
                ));
    }

}
