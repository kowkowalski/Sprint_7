package ru.yandex.praktikum.tests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.model.Order;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    @Parameterized.Parameter
    public List<String> colors;

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        });
    }

    private final OrderClient orderClient = new OrderClient(SPEC);

    @Test
    public void testCreateOrderWithDifferentColors() {
        Order order = Order.defaultOrderWithColors(colors);
        createOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }

    @Step("Создание заказа с цветами: {order.color}")
    private Response createOrder(Order order) {
        return orderClient.createOrder(order);
    }
}