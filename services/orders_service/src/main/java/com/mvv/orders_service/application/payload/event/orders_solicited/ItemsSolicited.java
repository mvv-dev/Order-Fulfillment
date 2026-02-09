package com.mvv.orders_service.application.payload.event.orders_solicited;

public record ItemsSolicited(
        String name,
        Integer quantity
) {
}
