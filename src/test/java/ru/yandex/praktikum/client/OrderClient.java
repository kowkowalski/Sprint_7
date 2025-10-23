package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class OrderClient {

    private final RequestSpecification requestSpec;

    public OrderClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    @Step("Создание заказа")
    public Response createOrder(Object body) {
        return requestSpec
                .body(body)
                .when()
                .post("/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return requestSpec
                .when()
                .get("/orders");
    }
}