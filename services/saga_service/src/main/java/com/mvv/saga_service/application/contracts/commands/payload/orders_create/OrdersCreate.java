package com.mvv.saga_service.application.contracts.commands.payload.orders_create;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.items.ItemsOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrdersCreate(
    UUID requestId,
    Customer customer,
    Card card,
    List<ItemsOrder> items,
    BigDecimal amount
) {
}
