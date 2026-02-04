package com.mvv.saga_service.contratcts.events;

public record ItemsOrderSolicited(
        String name,
        Integer quantity
) {
}
