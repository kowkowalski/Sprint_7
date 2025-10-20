package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.hamcrest.Matchers.equalTo;

public class CourierDuplicateCreateTest extends BaseTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId = -1;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("user" + System.currentTimeMillis(), "1234", "Ivan");
        courierClient.createCourier(courier);
        Response loginResponse = courierClient.loginCourier(courier);
        courierId = loginResponse.then().extract().path("id");
    }

    @Step("Создать дубликата курьера")
    private Response createDuplicateCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }

    @Test
    @Description("Проверка: нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        Response response = createDuplicateCourier(courier);
        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        if (courierId > 0) {
            courierClient.deleteCourier(courierId).then().statusCode(200);
        }
    }
}