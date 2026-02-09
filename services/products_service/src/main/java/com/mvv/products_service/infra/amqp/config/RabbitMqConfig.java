package com.mvv.products_service.infra.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final String EXCHANGE = "order-fulfillment";
    private static final String QUEUE = "products.commands.queue";
    private static final String ROUTING_KEY = "command.products.#";

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