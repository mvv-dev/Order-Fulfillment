package com.mvv.cards_service.application.payload.command;

import com.mvv.cards_service.application.payload.common.Card;
import com.mvv.cards_service.application.payload.common.Customer;
import com.mvv.cards_service.application.payload.common.ItemsReserved;
import com.mvv.cards_service.application.payload.common.StatusOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CardsDebit(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        UUID paymentId
) {
}
