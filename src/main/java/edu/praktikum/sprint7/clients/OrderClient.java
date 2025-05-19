package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String API_V1_ORDERS = "/api/v1/orders";

    @Step("Создание заказа: {order}")
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .post(API_V1_ORDERS);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .get(API_V1_ORDERS);
    }

    @Step("Принятие заказа. CourierId: {courierId}, Track: {track}")
    public Response acceptOrder(int courierId, int track) {
        return given()
                .queryParam("courierId", courierId)
                .queryParam("track", track)
                .put("/api/v1/orders/accept");
    }

    @Step("Получение заказа по треку: {track}")
    public Response getOrderByTrack(int track) {
        return given()
                .queryParam("t", track)
                .get("/api/v1/orders/track");
    }
}
