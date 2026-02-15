package com.mvv.cards_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.cards_service.application.payload.command.CardsDebit;
import com.mvv.cards_service.application.payload.common.envelope.Envelope;
import com.mvv.cards_service.application.usecase.DebitCardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardsCommandsConsumer {

    private final ObjectMapper objectMapper;
    private final DebitCardUseCase debitCardUseCase;

    @RabbitListener(queues = "cards.commands.queue")
    public void commandsListener(@Payload String message) {

        log.info("A command was recieved: {}", message);

        try {

            Envelope<JsonNode> envelope = objectMapper.readValue(message, new TypeReference<Envelope<JsonNode>>() {
            });

            switch (envelope.name()) {

                case "command.cards.debit" -> {

                    CardsDebit commandPayload = objectMapper.convertValue(envelope.payload(), CardsDebit.class);
                    Envelope<CardsDebit> commandEnvelope = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.correlationId(), envelope.source(), commandPayload
                    );
                    debitCardUseCase.execute(commandEnvelope);

                }

            }

        } catch (Exception e) {

            log.error("Error converting message payload", e);
            throw new RuntimeException("Error converting message payload", e);

        }
    }

}
