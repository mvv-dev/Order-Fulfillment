package com.mvv.orders_service.application.payload.command.orders_create;

import com.mvv.orders_service.application.payload.common.Card;
import com.mvv.orders_service.application.payload.common.Customer;
import com.mvv.orders_service.application.payload.common.ItemsOrder;

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
