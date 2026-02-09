package com.mvv.saga_service.infra.amqp.publisher;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.saga_service.application.contracts.commands.payload.products_check_items.ProductsCheckItems;
import com.mvv.saga_service.application.contracts.common.Envelope;
import com.mvv.saga_service.application.port.out.CommandPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SagaCommandsPublisher implements CommandPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;
    private final ObjectMapper objectMapper;


    @Override
    public void publish(Envelope<?> envelope) {

       try {
           String json = objectMapper.writeValueAsString(envelope);
            rabbitTemplate.convertAndSend(exchange.getName(), envelope.name(), json);
            System.out.println("Mensagem enviada: " + json);
       } catch (Exception e) {
           throw new RuntimeException("Error serializing/publishing message: " + envelope.name(), e);
       }

    }
}

