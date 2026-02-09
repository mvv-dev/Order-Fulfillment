package com.mvv.saga_service.application.contracts.events.payload.orders_created;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.items.ItemsOrder;
import com.mvv.saga_service.application.contracts.common.StatusOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdersCreated(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsOrder> items,
        BigDecimal amount,
        StatusOrder status
) {
}
