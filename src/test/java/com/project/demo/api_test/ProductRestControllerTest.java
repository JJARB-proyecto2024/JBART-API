package com.project.demo.api_test;

import com.project.demo.helpers.AuthTestHelper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/products";
    }

    @Test
    void getAllProducts_Success() {
        given()
                .header("Authorization", "Bearer " + AuthTestHelper.getUserToken())
                .when()
                .get("")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getAllBrands_Success() {
        given()
                .header("Authorization", "Bearer " + AuthTestHelper.getBrandToken())
                .when()
                .get("/brands")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getBrandsById_Success() {
        given()
        .header("Authorization", "Bearer " + AuthTestHelper.getUserToken())
                .when()
                .get("/brand/{id}", 5)
                .then()
                .statusCode(HttpStatus.OK.value());
    }


    @Test
    void getBrandById_NotFound() {
        given()
        .header("Authorization", "Bearer " + AuthTestHelper.getUserToken())
                .when()
                .get("/brand/{id}", -101)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
