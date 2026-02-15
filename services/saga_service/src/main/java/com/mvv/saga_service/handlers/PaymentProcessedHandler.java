package com.mvv.saga_service.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.orders_cancel.OrdersCancel;
import com.mvv.saga_service.application.contracts.commands.payload.orders_confirm.OrdersConfirm;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.contracts.common.MessageType;
import com.mvv.saga_service.application.contracts.events.payload.payment_processed.PaymentProcessed;
import com.mvv.saga_service.application.contracts.events.payload.payment_processed.StatusPayment;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentProcessedHandler {

    private final ObjectMapper objectMapper;
    private final CommandPublisherPort publisherPort;

    public void handle(Envelope<JsonNode> envelope) {

        PaymentProcessed eventPayload = objectMapper.convertValue(envelope.payload(), PaymentProcessed.class);
        Envelope<?> commandEnvelope;

        if (eventPayload.statusPayment().equals(StatusPayment.FAILED)) {

            OrdersCancel commandPayload = new OrdersCancel(
                    eventPayload.requestId(), eventPayload.customer(), eventPayload.card(), eventPayload.amount(),
                    eventPayload.statusOrder(), eventPayload.reservations(), eventPayload.statusPayment(),
                    eventPayload.totalDebited(), eventPayload.errors()
            );

            commandEnvelope = new Envelope<>(
                    UUID.randomUUID(), "command.orders.cancel", MessageType.COMMAND, Instant.now(),
                    envelope.correlationId(), envelope.messageId(), "saga-source", commandPayload
            );

            publisherPort.publish(commandEnvelope);

        } else {

            OrdersConfirm commandPayload = new OrdersConfirm(
                    eventPayload.requestId(), eventPayload.customer(), eventPayload.card(), eventPayload.amount(),
                    eventPayload.statusOrder(), eventPayload.reservations(), eventPayload.statusPayment(),
                    eventPayload.totalDebited()
            );

            commandEnvelope = new Envelope<>(
                    UUID.randomUUID(), "command.orders.confirm", MessageType.COMMAND, Instant.now(),
                    envelope.correlationId(), envelope.messageId(), "saga-source", commandPayload
            );

            publisherPort.publish(commandEnvelope);

        }

    }

}
