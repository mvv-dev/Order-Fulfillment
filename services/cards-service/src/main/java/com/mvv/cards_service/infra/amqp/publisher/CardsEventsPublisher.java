package com.mvv.cards_service.infra.amqp.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvv.cards_service.application.payload.common.envelope.Envelope;
import com.mvv.cards_service.application.port.out.CardsEventPublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardsEventsPublisher implements CardsEventPublisherPort {

    private final TopicExchange exchange;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper mapper;

    @Override
    public void publish(Envelope<?> envelope) {
        try {
            String json = mapper.writeValueAsString(envelope);
            rabbitTemplate.convertAndSend(exchange.getName(), envelope.name(), json);
            log.info("A new event was published: {}", envelope);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing/publishing message: " + envelope.name(), e);
        }
    }

}
