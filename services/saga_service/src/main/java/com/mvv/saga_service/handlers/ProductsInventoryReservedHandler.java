package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.orders_invalidate.OrdersInvalidate;
import com.mvv.saga_service.application.contracts.commands.payload.payments_process.PaymentsProcess;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.common.MessageType;
import com.mvv.saga_service.application.contracts.common.ReservationError;
import com.mvv.saga_service.application.contracts.common.StatusProduct;
import com.mvv.saga_service.application.contracts.events.payload.products_inventory_reserved.ProductsInventoryReserved;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
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


            OrdersInvalidate commandPayload = new OrdersInvalidate(
                    payload.requestId(), "RESERVATION_FAILED", reservationErrors
            );

            Envelope<OrdersInvalidate> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "command.orders.invalidate", MessageType.COMMAND,
                    Instant.now(), envelope.correlationId(), envelope.messageId(), "saga-service",
                    commandPayload
            );

            log.info("We were unable to proceed with the order due to the following products: {}",
                    reservationErrors);
            log.info("A message to cancel this order will be published.");
            publisherPort.publish(eventEnvelope);

        } else {

            PaymentsProcess commandPayload = new PaymentsProcess(
                    payload.requestId(), payload.customer(), payload.card(), payload.amount(), payload.statusOrder(),
                    payload.reservations()
            );

            Envelope<PaymentsProcess> eventEnvelope = new Envelope<>(
                    UUID.randomUUID(), "command.payments.process", MessageType.COMMAND, Instant.now(),
                    envelope.correlationId(), envelope.messageId(), "saga-service", commandPayload
            );

            log.info("Items were successfully reserved: {}", commandPayload);
            log.info("A message to start payment processing will be published.");
            publisherPort.publish(eventEnvelope);

        }



    }

}
