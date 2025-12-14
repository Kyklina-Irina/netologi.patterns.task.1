package netologi.patterns.task1.utils;

import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // Утилитный класс: приватный конструктор
public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    // Генерация валидного города из списка
    public static String generateCity() {
        String[] cities = {"Москва", "Санкт-Петербург", "Казань", "Новосибирск", "Екатеринбург", "Нижний Новгород"};
        return cities[faker.random().nextInt(cities.length)];
    }

    // Генерация валидной даты (через 3 дня от текущей)
    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    // Генерация валидного имени и фамилии
    public static String generateName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    // Генерация валидного номера телефона в формате +7XXXXXXXXXX
    public static String generatePhone() {
        return "+7" + faker.number().digits(10);
    }
}