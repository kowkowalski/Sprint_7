package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.praktikum.client.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest {

    @Step("Получить список заказов")
    private Response getOrders() {
        OrderClient orderClient = new OrderClient();
        return orderClient.getOrders();
    }

    @Test
    @Description("Проверка: список заказов возвращается успешно")
    public void testGetOrdersList() {
        Response response = getOrders();
        response.then().statusCode(200).body("orders", notNullValue());
    }
}