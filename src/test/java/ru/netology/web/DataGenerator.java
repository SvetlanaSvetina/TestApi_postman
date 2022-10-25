package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification; //  конструктор для создания спецификации ответа

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder() // создание спецификации
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void sendRequest(RegistrationUserData user) {  // создаем регистрационную форму по нужному нам шаблону
        // сам запрос
        RequestSpecification given = given();
        given.spec(requestSpec);
        given.body(new RegistrationUserData(   // передаём в теле объект, который будет преобразован в JSON
                        user.getLogin(),
                        user.getPassword(),
                        user.getStatus()))
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200);// дано
// ссылка на используемую спецификаци (выше)
// код 200 OK
    }

    // генерим логин пользователя
    public static String generateLogin() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().username();
    }

    // генерим пароль пользователя
    public static String generatePassword() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {
        }

        // генерим пользователя с учетом логина и пароля
        public static ru.netology.web.RegistrationUserData generateUser(String status) {
            return new RegistrationUserData(generateLogin(), generatePassword(), status);
        }

        // создаем статус
        public static RegistrationUserData registerUser(String status) {
            RegistrationUserData registerUser = generateUser(status);
            sendRequest(registerUser);
            return registerUser;
        }
    }
}
