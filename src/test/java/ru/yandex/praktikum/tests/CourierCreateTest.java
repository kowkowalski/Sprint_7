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

public class CourierCreateTest extends BaseTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    @Step("Подготовка данных для создания курьера")
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier("test_user_" + System.currentTimeMillis(), "1234", "Test Name");
    }

    @Test
    @Step("Создание нового курьера и проверка успешного ответа")
    public void testCreateCourierSuccess() {
        Response response = courierClient.createCourier(courier);
        response.then().statusCode(201).body("ok", equalTo(true));

        Response loginResponse = courierClient.loginCourier(courier);
        courierId = loginResponse.then().extract().path("id");
        loginResponse.then().statusCode(200).body("id", notNullValue());
    }

    @After
    @Step("Удаление созданного курьера после теста")
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}