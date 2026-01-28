package com.mvv.cards_service.application.usecase.command;

import com.mvv.cards_service.domain.model.Brand;

import java.math.BigDecimal;

public record CreateCardTypeCommand(
        String name, Brand brand, BigDecimal initialBalance) {
}
