package edu.praktikum.sprint7.clients;

import edu.praktikum.sprint7.models.Courier;
import edu.praktikum.sprint7.models.CourierCreds;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String API_V1_COURIER = "/api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step("Создание курьера: {courier}")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    @Step("Логин курьера: {creds}")
    public Response login(CourierCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    @Step("Удаление курьера по ID: {id}")
    public Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(API_V1_COURIER + "/" + id);
    }
}
