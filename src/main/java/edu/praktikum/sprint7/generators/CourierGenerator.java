package edu.praktikum.sprint7.generators;

import edu.praktikum.sprint7.models.Courier;

import static edu.praktikum.sprint7.utils.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .setLogin(randomString())
                .setPassword(randomString())
                .setFirstName(randomString());
    }
}
