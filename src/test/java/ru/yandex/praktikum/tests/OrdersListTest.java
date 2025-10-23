package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.client.OrderClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class OrdersListTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient(SPEC);

    @Test
    public void testGetOrdersList() {
        getOrdersList()
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }

    @Step("Получение списка заказов")
    private Response getOrdersList() {
        return orderClient.getOrdersList();
    }
}