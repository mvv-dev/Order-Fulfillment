package com.mvv.orders_service.infra.amqp.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.orders_service.infra.amqp.dto.OrderSolicitedEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;
    private final ObjectMapper objectMapper;

    public void publishOrderSolicited(OrderSolicitedEventDTO event){

        try {
            String json = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend(
                    exchange.getName(), "event.orders.solicited", json
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize OrderSolicitedEventDTO", e);
        }




    }



}
