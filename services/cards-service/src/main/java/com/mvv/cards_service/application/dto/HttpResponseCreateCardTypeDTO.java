package com.mvv.cards_service.application.dto;

import com.mvv.cards_service.domain.model.Brand;

import java.math.BigDecimal;
import java.util.UUID;

public record HttpResponseCreateCardTypeDTO (
        UUID id,
        String name,
        Brand brand,
        BigDecimal initialBalance

){
}
