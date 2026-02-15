package com.mvv.cards_service.application.payload.event;

import com.mvv.cards_service.application.payload.common.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CardDebitProcessed(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        UUID paymentId,
        StatusPayment statusPayment,
        BigDecimal totalDebited,
        List<ErrorCard> errors
) {
}
