package com.mvv.orders_service.infra.amqp.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.orders_service.application.port.out.OrdersEventPublisherPort;
import com.mvv.orders_service.application.payload.common.envelope.Envelope;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher implements OrdersEventPublisherPort {

    private final TopicExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;


    @Override
    public void publish(Envelope<?> envelope) {
        try {
            String json = mapper.writeValueAsString(envelope);
            rabbitTemplate.convertAndSend(exchange.getName(), envelope.name(), json);
            System.out.println("Publiquei uma mensagem: " + json);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing/publishing message: " + envelope.name(), e);
        }
    }


}
