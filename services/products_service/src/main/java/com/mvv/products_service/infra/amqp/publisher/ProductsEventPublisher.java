package com.mvv.products_service.infra.amqp.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.products_service.application.payload.common.envelope.Envelope;
import com.mvv.products_service.application.port.out.ProductsEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductsEventPublisher implements ProductsEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(Envelope<?> envelope) {

        try {
            String json = objectMapper.writeValueAsString(envelope);
            rabbitTemplate.convertAndSend(exchange.getName(), envelope.name(), json);
            System.out.println("Publiquei uma mensagem " + json);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing/publishing message: " + envelope.name(), e);
        }

    }
}
