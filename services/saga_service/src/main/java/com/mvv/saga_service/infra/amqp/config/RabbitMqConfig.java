package com.mvv.saga_service.infra.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final String EXCHANGE = "order-fulfillment";
    private static final String QUEUE = "saga.events.queue";

    @Bean
    public TopicExchange orderFulfillmentExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue ordersCommandsQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding ordersEventsBinding(Queue sagaQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(sagaQueue)
                .to(exchange)
                .with("event.orders.#");
    }

    @Bean
    public Binding productsEventsBinding(Queue sagaQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(sagaQueue)
                .to(exchange)
                .with("event.products.#");
    }

    @Bean
    public Binding paymentsEventsBinding(Queue sagaQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(sagaQueue)
                .to(exchange)
                .with("event.payments.#");
    }

}