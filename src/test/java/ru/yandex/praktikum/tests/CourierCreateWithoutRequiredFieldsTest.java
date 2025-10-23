package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreateWithoutRequiredFieldsTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient(SPEC);

    @Test
    public void testCreateCourierWithoutPassword() {
        Courier noPassword = new Courier("user" + System.currentTimeMillis(), "", "Ivan");

        createCourier(noPassword)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void testCreateCourierWithoutLogin() {
        Courier noLogin = new Courier("", "1234", "Ivan");

        createCourier(noLogin)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Создание курьера (некорректные данные): {courier}")
    private Response createCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }
}