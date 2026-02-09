package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.orders_cancel.OrdersCancel;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.common.MessageType;
import com.mvv.saga_service.application.contracts.common.ReservationError;
import com.mvv.saga_service.application.contracts.common.StatusProduct;
import com.mvv.saga_service.application.contracts.events.payload.products_inventory_reserved.ProductsInventoryReserved;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductsInventoryReservedHandler {

    private final ObjectMapper objectMapper;
    private final CommandPublisherPort publisherPort;

    public void handle(Envelope<JsonNode> envelope) {

        ProductsInventoryReserved payload = objectMapper.convertValue(envelope.payload(), ProductsInventoryReserved.class);

        if (payload.statusReservation().equals(StatusProduct.FAILED)) {

            List<ReservationError> reservationErrors = payload.errors().stream().map(
                    eventError -> new ReservationError(eventError.item(), eventError.code(),
                            eventError.message(), eventError.available())).toList();


            OrdersCancel commandPayload = new OrdersCancel(
                    payload.requestId(), "RESERVATION_FAILED", reservationErrors
            );

            Envelope<OrdersCancel> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "command.orders.cancel", MessageType.COMMAND,
                    Instant.now(), envelope.correlationId(), envelope.messageId(), "saga-service",
                    commandPayload
            );

            publisherPort.publish(eventEnvelope);

        }

    }

}
