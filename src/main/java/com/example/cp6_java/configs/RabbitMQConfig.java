package com.example.cp6_java.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Value("${rabbitmq.exchange.transaction}")
    private String transactionExchangeName;
    @Value("${rabbitmq.queue.transaction}")
    private String transactionQueueName;
    @Value("${rabbitmq.routingkey.transaction}")
    private String transactionRoutingKey;

 
    @Value("${rabbitmq.queue.response}") 
    private String responseQueueName;
    @Value("${rabbitmq.exchange.response}") 
    private String responseExchangeName;
    @Value("${rabbitmq.routingkey.response}") 
    private String responseRoutingKey;

   
    @Bean
    public Queue transactionQueue() {
        return new Queue(transactionQueueName, true);
    }

    @Bean
    public DirectExchange transactionExchange() { 
        return new DirectExchange(transactionExchangeName, true, false);
    }
    @Bean
    public Binding transactionBinding() {
        return BindingBuilder.bind(transactionQueue())
                .to(transactionExchange())
                .with(transactionRoutingKey);
    }

 
    @Bean
    public Queue responseQueue() {
        return new Queue(responseQueueName, true);
    }
   
    @Bean
    public TopicExchange responseExchange() {
        return new TopicExchange(responseExchangeName);
    }
    @Bean
    public Binding responseBinding(Queue responseQueue, TopicExchange responseExchange) {
        return BindingBuilder.bind(responseQueue)
                .to(responseExchange)
                .with(responseRoutingKey);
    }

  
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}