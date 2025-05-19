package edu.praktikum.sprint7.generators;

import com.github.javafaker.Faker;
import edu.praktikum.sprint7.models.Courier;

public class CourierGenerator {

    private static final Faker faker = new Faker();

    public static Courier randomCourier() {
        return new Courier()
                .setLogin(faker.name().username())
                .setPassword(faker.internet().password())
                .setFirstName(faker.name().firstName());
    }
}
