package com.mvv.saga_service.application;

import com.mvv.saga_service.contratcts.MessageType;
import com.mvv.saga_service.contratcts.commands.ItemsProductCheck;
import com.mvv.saga_service.contratcts.commands.ProductsCheckItemsCommand;
import com.mvv.saga_service.contratcts.events.OrderSolicited;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SagaEventCommandMapper {

    public ProductsCheckItemsCommand productsCheckItemsCommand(OrderSolicited orderSolicited) {

        UUID messageId = UUID.randomUUID();
        String name = "products.check.items";
        MessageType type = MessageType.COMMAND;
        UUID correlationId = orderSolicited.correlationId();
        UUID orderId = orderSolicited.orderId();
        UUID causationId = orderSolicited.messageId();
        Instant ocurredAt = Instant.now();
        List<ItemsProductCheck> items = orderSolicited.items().stream().map(
                itemsOrderSolicited -> new ItemsProductCheck(itemsOrderSolicited.name(),
                        itemsOrderSolicited.quantity())
        ).toList();

        return new ProductsCheckItemsCommand(
                messageId, name, type, correlationId, orderId, causationId, ocurredAt, items
        );

    }

}
