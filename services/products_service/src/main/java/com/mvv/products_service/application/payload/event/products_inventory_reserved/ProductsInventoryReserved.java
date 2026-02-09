package com.mvv.products_service.application.payload.event.products_inventory_reserved;

import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.StatusOrder;
import com.mvv.products_service.application.payload.common.StatusProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductsInventoryReserved(
        UUID requestId,
        Customer customer,
        Card card,
        BigDecimal amount,
        StatusOrder statusOrder,
        List<ItemsReserved> reservations,
        List<ReservationError> errors,
        StatusProduct statusReservation
) {
}
