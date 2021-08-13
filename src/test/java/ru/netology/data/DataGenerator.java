package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        String date = formatter.format(LocalDate.now().plusDays(shift));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(Locale.forLanguageTag(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }
}
