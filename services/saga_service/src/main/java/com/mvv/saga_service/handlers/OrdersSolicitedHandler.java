package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.products_check_items.ProductsCheckItems;
import com.mvv.saga_service.application.contracts.common.*;
import com.mvv.saga_service.application.contracts.common.items.ItemsSolicited;
import com.mvv.saga_service.application.contracts.events.payload.orders_solicited.OrdersSolicited;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrdersSolicitedHandler {

    private final CommandPublisherPort publisherPort;
    private final ObjectMapper objectMapper;

    public void handle(Envelope<JsonNode> envelope) {

        log.info("A new order was solicited: {}", envelope.payload());

        OrdersSolicited eventPayload = objectMapper.convertValue(
                envelope.payload(), OrdersSolicited.class
        );

        ProductsCheckItems commandPayload = new ProductsCheckItems(
                eventPayload.requestId(), new Customer(eventPayload.customer().keycloakUserId()),
                new Card(eventPayload.card().cardId()), eventPayload.items().stream().
                map(itemEvent -> new ItemsSolicited(itemEvent.name(), itemEvent.quantity())).toList()
        );

        Envelope<ProductsCheckItems> commandEnvelope = new Envelope<>(
                UUID.randomUUID(), "command.products.check_items", MessageType.COMMAND,
                Instant.now(), eventPayload.requestId(), envelope.messageId(), "saga-service",
                commandPayload
        );

        log.info("A new message to check order items will be published: {}", envelope.payload());
        publisherPort.publish(commandEnvelope);

    }

}
