package com.mvv.products_service.application.payload.common.items;

public record ItemsError(
        String item,
        String code,
        String message
) {
}
