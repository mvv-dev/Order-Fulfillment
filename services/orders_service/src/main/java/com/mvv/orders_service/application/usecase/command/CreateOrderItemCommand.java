package com.mvv.orders_service.application.usecase.command;

public record CreateOrderItemCommand(
        String name,
        int quantity
) {
}
