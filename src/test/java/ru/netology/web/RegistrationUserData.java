package ru.netology.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data// аннотация, которая применяет сразу несколько аннотаций Lombok
@RequiredArgsConstructor
// создаёт конструктор с требуемыми аргументами

public class RegistrationUserData {
    private final String login;
    private final String password;
    private final String status;
}
