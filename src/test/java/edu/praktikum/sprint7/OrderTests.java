package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.CourierClient;
import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.generators.OrderGenerator;
import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierId;
import edu.praktikum.sprint7.models.Order;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static edu.praktikum.sprint7.generators.OrderGenerator.randomOrder;
import static edu.praktikum.sprint7.generators.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.models.CourierCreds.credsFromCourier;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderTests {

    private final OrderClient orderClient = new OrderClient();
    private final CourierClient courierClient = new CourierClient();
    private int courierId;

    @Test
    public void getOrdersShouldReturnList() {
        Response response = orderClient.getOrders();
        assertEquals("Ожидался статус 200 при получении списка заказов", SC_OK, response.statusCode());
        response.then().body("orders", notNullValue());
    }

    @Test
    public void acceptOrderShouldReturnOkTrue() {
        Courier courier = randomCourier();
        courierClient.create(courier);
        Response loginResponse = courierClient.login(credsFromCourier(courier));
        courierId = loginResponse.as(CourierId.class).getId();

        Order order = randomOrder();
        int track = orderClient.create(order).then().extract().path("track");

        Response acceptResponse = orderClient.acceptOrder(courierId, track);
        assertEquals("Ожидался статус 200 при принятии заказа", SC_OK, acceptResponse.statusCode());
        acceptResponse.then().body("ok", equalTo(true));
    }

    @Test
    public void getOrderByTrackShouldReturnOrder() {
        Order order = randomOrder();
        int track = orderClient.create(order).then().extract().path("track");

        Response response = orderClient.getOrderByTrack(track);
        assertEquals("Ожидался статус 200 при получении заказа по треку", SC_OK, response.statusCode());
        assertNotNull("Объект заказа должен быть в ответе", response.then().extract().path("order"));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
}
