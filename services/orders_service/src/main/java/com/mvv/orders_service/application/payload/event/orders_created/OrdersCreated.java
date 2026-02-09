package com.mvv.orders_service.application.payload.event.orders_created;

import com.mvv.orders_service.application.payload.common.Card;
import com.mvv.orders_service.application.payload.common.Customer;
import com.mvv.orders_service.application.payload.common.ItemsOrder;
import com.mvv.orders_service.application.payload.common.StatusOrder;

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
