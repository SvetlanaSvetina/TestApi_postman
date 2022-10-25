package ru.netology.web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static groovy.xml.dom.DOMCategory.setValue;
import static ru.netology.web.DataGenerator.Registration.generateUser;
import static ru.netology.web.DataGenerator.Registration.registerUser;
import static ru.netology.web.DataGenerator.generateLogin;
import static ru.netology.web.DataGenerator.generatePassword;


public class RegistrationUserTest {

    @BeforeEach
    public void setup() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldUserRegistredSuccessfully() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = registerUser("active"); // Локальная переменная, используется для хранения промежуточных результатов вычислений
        $(By.cssSelector("[data-test-id=login] input")).setValue(registeredUser.getLogin());
        $(By.cssSelector("[data-test-id=password] input")).setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $(".heading").shouldHave(Condition.text("Личный кабинет")).click();

    }

    @Test
    public void shouldUserRegistredInvalidLogin() {

        var registeredUser = registerUser("active");
        var invalidLogin= generateLogin(); //  Локальная переменная, используется для хранения промежуточных результатов вычислений
        $(By.cssSelector("[data-test-id=login] input")).setValue(invalidLogin);
        $(By.cssSelector("[data-test-id=password] input")).setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).click();

    }

    @Test
    public void shouldUserRegistredInvalidPassword(){

        var registeredUser = registerUser("active");
        var invalidPassword= generatePassword(); // Локальная переменная, используется для хранения промежуточных результатов вычислений
        $(By.cssSelector("[data-test-id=login] input")).setValue(registeredUser.getLogin());
        $(By.cssSelector("[data-test-id=password] input")).setValue(invalidPassword);
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).click();
    }

    @Test
    public void shouldUserRegistredNotActive() {
        var notRegistredUser = generateUser("active"); // Локальная переменная, используется для хранения промежуточных результатов вычислений
        SelenideElement selenideElement = $(By.cssSelector("[data-test-id=login] input")).setValue(notRegistredUser.getLogin());
        $(By.cssSelector("[data-test-id=password] input")).setValue(notRegistredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=error-notification]").shouldHave(Condition.visible).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).click();
    }
}