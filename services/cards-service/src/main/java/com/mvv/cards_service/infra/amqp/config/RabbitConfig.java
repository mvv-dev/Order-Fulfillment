package com.mvv.cards_service.infra.amqp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    private static final String EXCHANGE = "order-fulfillment";
    private static final String QUEUE = "cards.commands.queue";
    private static final String ROUTING_KEY = "command.cards.#";

    @Bean
    public TopicExchange orderFulfillmentExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue ordersCommandsQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding orderCommandsQueueBinding(Queue orderCommandsQueue, TopicExchange exchange) {

        return BindingBuilder
                .bind(orderCommandsQueue)
                .to(exchange)
                .with(ROUTING_KEY);

    }

}
