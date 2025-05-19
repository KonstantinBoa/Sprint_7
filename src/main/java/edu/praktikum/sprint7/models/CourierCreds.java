package edu.praktikum.sprint7.models;

public class CourierCreds {

    private String login;
    private String password;

    // 🔧 изменено: теперь public
    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFromCourier(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    // 💡 желательно добавить геттеры (по желанию)
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // 💡 и toString() — для удобства в логах и Allure
    @Override
    public String toString() {
        return "CourierCreds{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

