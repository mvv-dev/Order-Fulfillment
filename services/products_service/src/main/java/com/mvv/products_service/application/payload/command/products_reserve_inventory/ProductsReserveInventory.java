package com.mvv.products_service.application.payload.command.products_reserve_inventory;

import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.items.ItemsOrder;
import com.mvv.products_service.application.payload.common.StatusOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductsReserveInventory(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsOrder> items,
        BigDecimal amount,
        StatusOrder status
) {
}
