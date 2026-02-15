package com.mvv.products_service.application.payload.event.products_items_released;

import com.mvv.products_service.application.payload.common.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductsItemsReleased (
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReleased> reservations,
        StatusPayment statusPayment,
        BigDecimal totalDebited
){
}
