package com.HealthCheckupService.HealthCheckupService.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "checkup-exchange";
    public static final String QUEUE_NAME = "checkup-queue";
    public static final String ROUTING_KEY = "checkup.created";

    @Bean
    public TopicExchange checkupExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue checkupQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }


}
