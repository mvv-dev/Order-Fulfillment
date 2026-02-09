package com.mvv.saga_service.application.contracts.events.payload.orders_solicited;

import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.items.ItemsSolicited;

import java.util.List;
import java.util.UUID;

public record OrdersSolicited(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsSolicited> items
) {
}
