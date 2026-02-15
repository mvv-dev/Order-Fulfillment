package com.mvv.payments_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.payments_service.application.payload.command.PaymentsProcess;
import com.mvv.payments_service.application.payload.common.envelope.Envelope;
import com.mvv.payments_service.application.payload.event.CardDebitProcessed;
import com.mvv.payments_service.application.usecase.ConfirmPaymentProcessUseCase;
import com.mvv.payments_service.application.usecase.ProcessPaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentsCommandConsumer {

    private final ObjectMapper objectMapper;
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final ConfirmPaymentProcessUseCase confirmPaymentProcessUseCase;

    @RabbitListener(queues = "payments.commands.queue")
    public void commandsListener(@Payload String message) {

        log.info("A command was recieved: {}", message);

        try {

            Envelope<JsonNode> envelope = objectMapper.readValue(message, new TypeReference<Envelope<JsonNode>>() {
            });

            switch (envelope.name()) {

                case "command.payments.process" -> {

                    PaymentsProcess commandPayload = objectMapper.convertValue(envelope.payload(), PaymentsProcess.class);
                    Envelope<PaymentsProcess> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.causationId(), envelope.source(), commandPayload
                    );

                    processPaymentUseCase.execute(commandEnvelope);
                }

                case "event.cards.debit.processed" -> {

                    CardDebitProcessed eventPayload = objectMapper.convertValue(envelope.payload(),
                            CardDebitProcessed.class);
                    Envelope<CardDebitProcessed> eventEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.causationId(), envelope.source(), eventPayload
                    );

                    confirmPaymentProcessUseCase.execute(eventEnvelope);

                }

            }

        } catch (Exception e) {

            log.error("Error converting message payload");
            throw new RuntimeException("Error converting message payload");

        }

    }

}
