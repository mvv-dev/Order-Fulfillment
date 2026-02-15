package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.products_release_items.ProductsReleaseItems;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.common.MessageType;
import com.mvv.saga_service.application.contracts.events.payload.orders_cancelled.OrdersCancelled;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdersCancelledHandler {

    private final ObjectMapper objectMapper;
    private final CommandPublisherPort commandPublisherPort;

    public void handle(Envelope<JsonNode> envelope) {

        OrdersCancelled eventPayload = objectMapper.convertValue(envelope.payload(), OrdersCancelled.class);

        log.info("Order was sucessfully cancelled: {}", eventPayload);

        ProductsReleaseItems commandPayload = new ProductsReleaseItems(
                eventPayload.requestId(), eventPayload.customer(), eventPayload.card(), eventPayload.amount(),
                eventPayload.statusOrder(), eventPayload.reservations(), eventPayload.statusPayment(), eventPayload.totalDebited()
        );

        Envelope<ProductsReleaseItems> commandEnvelope = new Envelope<>(
                UUID.randomUUID(), "command.products.release_items", MessageType.COMMAND, Instant.now(),
                commandPayload.requestId(), envelope.messageId(), "saga-source", commandPayload
        );

        commandPublisherPort.publish(commandEnvelope);

    }

}
