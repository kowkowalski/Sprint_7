package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreateTest extends BaseTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer createdCourierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient(SPEC);
        courier = new Courier("courierLogin", "courierPassword", "courierName");
    }

    @After
    public void tearDown() {
        if (createdCourierId != null) {
            deleteCourierSafely(createdCourierId);
        }
    }

    @Test
    public void testCreateCourierSuccess() {
        Response createResp = createCourier(courier);
        createResp.then().statusCode(SC_CREATED).body("ok", equalTo(true));

        Response loginResp = loginCourier(courier);
        loginResp.then().statusCode(SC_OK).body("id", notNullValue());

        createdCourierId = loginResp.then().extract().path("id");
    }

    @Step("Создание курьера: {courier}")
    private Response createCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }

    @Step("Логин курьера: {courier.login}")
    private Response loginCourier(Courier courier) {
        return courierClient.loginCourier(courier);
    }
}