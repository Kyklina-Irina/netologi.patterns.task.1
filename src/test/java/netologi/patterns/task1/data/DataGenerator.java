package netologi.patterns.task1.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    // Приватный конструктор для утилитного класса
    private DataGenerator() {}

    // Генерация данных для формы
    public static UserInfo generateUserInfo() {
        String city = "Казань"; // Город фиксированный, как в примере
        String name = faker.name().fullName();
        String phone = "+7" + faker.number().digits(10); // Генерируем номер телефона

        return new UserInfo(city, name, phone);
    }

    // Генерация даты в нужном формате (dd.MM.yyyy)
    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // Класс для хранения данных пользователя
    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}