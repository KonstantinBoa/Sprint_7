package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;
import edu.praktikum.sprint7.models.CourierId;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.models.CourierCreds.credsFromCourier;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class CourierTests {

    private final CourierClient courierClient = new CourierClient();
    private int id;

    @Test
    public void createCourier() {
        Courier courier = randomCourier();

        Response response = courierClient.create(courier);
        assertEquals("Некорректный статус-код", SC_CREATED, response.statusCode());

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        id = loginResponse.as(CourierId.class).getId();
        assertEquals("Некорректный статус-код", SC_OK, loginResponse.statusCode());
    }

    @Test
    public void createDuplicateCourier_shouldReturn409() {
        Courier courier = randomCourier();

        Response firstResponse = courierClient.create(courier);
        assertEquals("Курьер не был создан", SC_CREATED, firstResponse.statusCode());

        Response secondResponse = courierClient.create(courier);
        assertEquals("Должен вернуться 409 Conflict", SC_CONFLICT, secondResponse.statusCode());
        secondResponse.then().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        id = loginResponse.as(CourierId.class).getId();
    }

    @Test
    public void createCourierWithoutLogin_shouldReturn400() {
        Courier courier = new Courier()
                .setPassword("somePassword")
                .setFirstName("NoLogin");

        Response response = courierClient.create(courier);

        assertEquals("Ожидался статус 400 при отсутствии логина", SC_BAD_REQUEST, response.statusCode());
        response.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPassword_shouldReturn400() {
        Courier courier = new Courier()
                .setLogin("noPassword_" + System.currentTimeMillis())
                .setFirstName("NoPassword");

        Response response = courierClient.create(courier);

        assertEquals("Ожидался статус 400 при отсутствии пароля", SC_BAD_REQUEST, response.statusCode());
        response.then().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void loginWithIncorrectCredentials_shouldReturn404() {
        CourierCreds creds = new CourierCreds("invalidLogin", "invalidPassword");

        Response response = courierClient.login(creds);

        assertEquals("Ожидался статус 404 при неверных данных", SC_NOT_FOUND, response.statusCode());
        response.then().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLogin_shouldReturn400() {
        CourierCreds creds = new CourierCreds(null, "somePassword");

        Response response = courierClient.login(creds);

        assertEquals("Ожидался статус 400 при отсутствии логина", SC_BAD_REQUEST, response.statusCode());
        response.then().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPassword_shouldReturn400() {
        CourierCreds creds = new CourierCreds("someLogin", null);

        Response response = courierClient.login(creds);

        assertEquals("Ожидался статус 400 при отсутствии пароля", SC_BAD_REQUEST, response.statusCode());
        response.then().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        if (id != 0) {
            courierClient.delete(id);
        }
    }
}

