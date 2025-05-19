package edu.praktikum.sprint7;

import edu.praktikum.sprint7.clients.OrderClient;
import edu.praktikum.sprint7.models.Order;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static edu.praktikum.sprint7.generators.OrderGenerator.createOrder;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderParameterizedTests {

    private final OrderClient orderClient = new OrderClient();
    private final List<String> color;

    public OrderParameterizedTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")
    public static Collection<Object[]> getColors() {
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        });
    }

    @Test
    public void createOrderShouldReturnTrack() {
        Order order = createOrder(color);

        Response response = orderClient.create(order);
        assertEquals("Ожидался статус 201 при создании заказа", SC_CREATED, response.statusCode());
        response.then().body("track", notNullValue());
    }
}

