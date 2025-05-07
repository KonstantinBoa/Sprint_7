package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierId;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.models.CourierCreds.credsFromCourier;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
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

    @After
    public void tearDown() {
        courierClient.delete(id);
    }
}
