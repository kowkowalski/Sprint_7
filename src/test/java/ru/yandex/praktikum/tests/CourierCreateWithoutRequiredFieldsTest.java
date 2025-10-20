package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.model.Courier;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateWithoutRequiredFieldsTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient();

    @Step("Создать курьера без обязательных полей")
    private Response createCourier(Courier courier) {
        return courierClient.createCourier(courier);
    }

    @Test
    @Description("Проверка: нельзя создать курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        Courier courier = new Courier("user" + System.currentTimeMillis(), "", "Ivan");
        Response response = createCourier(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Проверка: нельзя создать курьера без логина")
    public void testCreateCourierWithoutLogin() {
        Courier courier = new Courier("", "1234", "Ivan");
        Response response = createCourier(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}