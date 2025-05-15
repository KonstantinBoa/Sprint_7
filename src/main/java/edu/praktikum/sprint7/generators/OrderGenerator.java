package edu.praktikum.sprint7.generators;

import edu.praktikum.sprint7.models.Order;

import java.util.Collections;
import java.util.List;

public class OrderGenerator {

    public static Order createOrder(List<String> colors) {
        return new Order(
                "Иван", "Иванов", "Москва, Кремль 1", "4",
                "+79998887766", 5, "2025-05-14", "Позвоните заранее", colors
        );
    }

    public static Order createOrderWithOneColor(String color) {
        return createOrder(Collections.singletonList(color));
    }

    public static Order createOrderWithoutColor() {
        return createOrder(Collections.emptyList());
    }

    public static Order createOrderWithTwoColors() {
        return createOrder(List.of("BLACK", "GREY"));
    }

    // 🎯 Новый метод — используется в тестах как стандартный заказ
    public static Order randomOrder() {
        return createOrderWithOneColor("BLACK");
    }
}
