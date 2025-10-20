package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Создание заказа")
    public Response createOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .post("/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .get("/orders");
    }
}