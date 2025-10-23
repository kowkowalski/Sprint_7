package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CourierClient {

    private final RequestSpecification requestSpec;

    public CourierClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("Создание курьера")
    public Response createCourier(Object body) {
        return requestSpec
                .body(body)
                .when()
                .post("/courier");
    }

    @Step("Авторизация курьера")
    public Response loginCourier(Object body) {
        return requestSpec
                .body(body)
                .when()
                .post("/courier/login");
    }

    @Step("Удаление курьера с id = {0}")
    public Response deleteCourier(int id) {
        return requestSpec
                .when()
                .delete("/courier/" + id);
    }
}