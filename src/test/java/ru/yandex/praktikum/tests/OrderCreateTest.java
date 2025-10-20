package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.model.Order;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest {

    private final List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Object[][] getColors() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Step("Создать заказ с цветом: {0}")
    private Response createOrder(Order order) {
        OrderClient orderClient = new OrderClient();
        return orderClient.createOrder(order);
    }

    @Test
    @Description("Проверка: заказ можно создать с разными вариантами цвета")
    public void testCreateOrderWithDifferentColors() {
        Order order = new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2025-10-20",
                "Saske, come back!",
                color
        );

        Response response = createOrder(order);
        response.then().statusCode(201).body("track", notNullValue());
    }
}