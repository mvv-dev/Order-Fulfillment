package com.mvv.saga_service.application.contracts.commands.payload.products_check_items;


import com.mvv.saga_service.application.contracts.common.Card;
import com.mvv.saga_service.application.contracts.common.Customer;
import com.mvv.saga_service.application.contracts.common.items.ItemsSolicited;

import java.util.List;
import java.util.UUID;

public record ProductsCheckItems(
        UUID requestId,
        Customer customer,
        Card card,
        List<ItemsSolicited> items
) {
}
