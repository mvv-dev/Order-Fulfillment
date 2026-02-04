package com.mvv.orders_service.infra.amqp.dto;

public record Items(
        String name,
        Integer quantity
) {
}
