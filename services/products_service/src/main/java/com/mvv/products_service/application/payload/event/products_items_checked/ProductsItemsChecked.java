package com.mvv.products_service.application.payload.event.products_items_checked;

import com.mvv.products_service.application.payload.common.Card;
import com.mvv.products_service.application.payload.common.Customer;
import com.mvv.products_service.application.payload.common.items.ItemsError;
import com.mvv.products_service.application.payload.common.items.ItemsOrder;
import com.mvv.products_service.application.payload.common.StatusProduct;


import java.util.List;
import java.util.UUID;

public record ProductsItemsChecked(
        UUID requestId,
        Customer customer,
        Card card,
        StatusProduct statusProduct,
        List<ItemsOrder> itemsOrder,
        List<ItemsError> errors
) {
}
