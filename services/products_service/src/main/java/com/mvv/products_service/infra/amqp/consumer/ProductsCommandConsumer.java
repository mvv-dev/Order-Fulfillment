package com.mvv.products_service.infra.amqp.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.products_service.application.payload.command.products_release_items.ProductsReleaseItems;
import com.mvv.products_service.application.payload.command.products_reserve_inventory.ProductsReserveInventory;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.payload.command.products_check_items.ProductsCheckItems;
import com.mvv.products_service.application.usecase.CheckItemsUseCase;
import com.mvv.products_service.application.usecase.ReleaseItemsUseCase;
import com.mvv.products_service.application.usecase.ReserveItemsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductsCommandConsumer {

    private final ObjectMapper objectMapper;
    private final CheckItemsUseCase checkItemsUseCase;
    private final ReserveItemsUseCase reserveItemsUseCase;
    private final ReleaseItemsUseCase releaseItemsUseCase;

    @RabbitListener(queues = "products.commands.queue")
    public void commandsListener(@Payload String message) {

        log.info("A command was recieved: {}", message);

        try {

            Envelope<JsonNode> envelope = objectMapper.readValue(message, new TypeReference<Envelope<JsonNode>>() {
            });

            switch (envelope.name()) {

                case "command.products.check_items" -> {

                    ProductsCheckItems payload = objectMapper.convertValue(envelope.payload(), ProductsCheckItems.class);
                    Envelope<ProductsCheckItems> envelopeTyped = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.causationId(), envelope.source(), payload
                    );
                    checkItemsUseCase.execute(envelopeTyped);
                }

                case "command.products.reserve_iventory" -> {

                    ProductsReserveInventory payload = objectMapper.convertValue(envelope.payload(),
                            ProductsReserveInventory.class);
                    Envelope<ProductsReserveInventory> envelopeTyped = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.causationId(), envelope.source(), payload
                    );
                    reserveItemsUseCase.execute(envelopeTyped);

                }

                case "command.products.release_items" -> {

                    ProductsReleaseItems commandPayload = objectMapper.convertValue(envelope.payload(),
                            ProductsReleaseItems.class);
                    Envelope<ProductsReleaseItems> envelopeTyped = new Envelope<>(
                            envelope.messageId(), envelope.name(), envelope.type(), envelope.ocurredAt(),
                            envelope.correlationId(), envelope.causationId(), envelope.source(), commandPayload
                    );
                    releaseItemsUseCase.execute(envelopeTyped);

                }

            }


        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON");
        }

    }

}
