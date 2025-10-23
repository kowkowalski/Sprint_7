package ru.yandex.praktikum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public static Order defaultOrderWithColors(List<String> colors) {
        return new Order(
                "Ivan",
                "Petrov",
                "Lenina 1",
                "4",
                "+79991234567",
                5,
                "2025-12-31",
                "No comment",
                colors
        );
    }
}