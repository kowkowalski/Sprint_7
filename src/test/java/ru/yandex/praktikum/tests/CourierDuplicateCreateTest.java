package ru.yandex.praktikum.tests;

import static org.hamcrest.Matchers.containsString;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierDuplicateCreateTest extends BaseTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer createdCourierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient(SPEC);
        courier = new Courier("user" + System.currentTimeMillis(), "1234", "Ivan");

        createCourier(courier).then().statusCode(SC_CREATED).body("ok", equalTo(true));
        Response login = loginCourier(courier).then().statusCode(SC_OK).body("id", notNullValue()).extract().response();
        createdCourierId = login.path("id");
    }

    @After
    public void tearDown() {
        if (createdCourierId != null) {
            deleteCourierSafely(createdCourierId);
        }
    }

    @Test
    public void testCreateDuplicateCourier() {
        createCourier(courier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
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