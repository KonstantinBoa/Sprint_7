package edu.praktikum.sprint7.models;

public class CourierCreds {

    private String login;

    private String password;

    private CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFromCourier(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }
}
