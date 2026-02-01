package com.mvv.products_service.application.controller.usecase.command;

import java.math.BigDecimal;
import java.util.UUID;

public record SaveProductCommand(
        UUID id,
        String name,
        BigDecimal price,
        Integer quantityLeft
) {
}
