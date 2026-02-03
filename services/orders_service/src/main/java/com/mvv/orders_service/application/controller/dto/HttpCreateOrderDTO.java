package com.mvv.orders_service.application.controller.dto;

import java.util.List;
import java.util.UUID;

public record HttpCreateOrderDTO(
        List<HttpProductDTO> products,
        UUID cardId
) {
}
