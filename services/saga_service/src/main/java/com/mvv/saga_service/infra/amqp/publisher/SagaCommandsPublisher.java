package com.mvv.saga_service.infra.amqp.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.contratcts.commands.ProductsCheckItemsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaCommandsPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;
    private final ObjectMapper objectMapper;

    public void publishProductsCheckItems(ProductsCheckItemsCommand cmd) {

        System.out.println("Prossegui, vou publicar essa mensagem para products");

        try {

            String json = objectMapper.writeValueAsString(cmd);
            rabbitTemplate.convertAndSend(
                    exchange.getName(),
                    "command.products.check_items", json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize command to JSON", e);
        }

    }

}
