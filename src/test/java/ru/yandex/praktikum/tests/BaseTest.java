package ru.yandex.praktikum.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import io.restassured.response.Response;

public class BaseTest {

    protected static RequestSpecification SPEC;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.basePath = "/api/v1";
        RestAssured.filters(new AllureRestAssured());

        SPEC = RestAssured
                .given()
                .header("Content-Type", "application/json");
    }

    protected static void deleteCourierSafely(Integer courierId) {
        if (courierId != null) {
            RestAssured
                    .given()
                    .delete("/courier/" + courierId);
        }
    }
}