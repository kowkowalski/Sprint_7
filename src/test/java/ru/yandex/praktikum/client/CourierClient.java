package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient {

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/courier");
    }

    @Step("Авторизация курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/courier/login");
    }

    @Step("Удаление курьера с id = {0}")
    public Response deleteCourier(int id) {
        return given()
                .when()
                .delete("/courier/" + id);
    }
}