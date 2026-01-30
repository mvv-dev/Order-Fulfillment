package com.mvv.cards_service.application.dto;

import com.mvv.cards_service.domain.model.CardType;

import java.math.BigDecimal;
import java.util.UUID;

public record HttpResponseSearchCardDTO(
        UUID id,
        CardType cardType,
        UUID kecloak_user_id,
        BigDecimal balance
) {
}
