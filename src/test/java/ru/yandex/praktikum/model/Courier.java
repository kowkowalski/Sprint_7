package ru.yandex.praktikum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    private String login;
    private String password;
    private String firstName;
}