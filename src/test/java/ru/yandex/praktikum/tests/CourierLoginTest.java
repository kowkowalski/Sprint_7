package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {

    private CourierClient courierClient;
    private Courier validCourier;
    private Integer createdCourierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient(SPEC);

        String login = "testLogin_" + System.currentTimeMillis();
        validCourier = new Courier(login, "testPassword", "TestName");

        createCourier(validCourier).then().statusCode(SC_CREATED).body("ok", equalTo(true));

        Response loginResp = loginCourier(validCourier).then().statusCode(SC_OK).body("id", notNullValue()).extract().response();
        createdCourierId = loginResp.path("id");
    }

    @After
    public void tearDown() {
        if (createdCourierId != null) {
            deleteCourierSafely(createdCourierId);
        }
    }

    @Test
    public void testCourierLoginSuccess() {
        loginCourier(validCourier)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    public void testCourierLoginWithoutLogin() {
        Courier noLogin = new Courier("", validCourier.getPassword(), validCourier.getFirstName());

        loginCourier(noLogin)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testCourierLoginWithoutPassword() {
        Courier noPassword = new Courier(validCourier.getLogin(), "", validCourier.getFirstName());

        loginCourier(noPassword)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void testCourierLoginWithWrongCredentials() {
        Courier wrong = new Courier("wrongLogin", "wrongPass", "name");

        loginCourier(wrong)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", anyOf(
                        equalTo("Учетная запись не найдена"),
                        equalTo("Учетная запись не найдена.") // на случай другой пунктуации
                ));
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