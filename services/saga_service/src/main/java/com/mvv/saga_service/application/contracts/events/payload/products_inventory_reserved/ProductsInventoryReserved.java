package com.mvv.saga_service.application.contracts.events.payload.products_inventory_reserved;

import com.mvv.saga_service.application.contracts.common.*;

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
