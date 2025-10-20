package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends BaseTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    @Step("Создание курьера перед тестом логина")
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("login_user_" + System.currentTimeMillis(), "1234", "Log Name");
        courierClient.createCourier(courier);

        Response loginResponse = courierClient.loginCourier(courier);
        courierId = loginResponse.then().extract().path("id");
    }

    @Test
    @Step("Проверка успешного логина курьера")
    public void testCourierLoginSuccess() {
        Response response = courierClient.loginCourier(courier);
        response.then().statusCode(200).body("id", notNullValue());
    }

    @After
    @Step("Удаление курьера после теста логина")
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}