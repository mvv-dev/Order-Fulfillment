package com.mvv.products_service.application.payload.command.products_release_items;

import com.mvv.products_service.application.payload.common.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductsReleaseItems(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        StatusPayment statusPayment,
        BigDecimal totalDebited
) {
}
