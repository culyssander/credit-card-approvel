package br.com.santander.accountservice.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static br.com.santander.accountservice.util.Constants.*;

@Configuration
@Profile("!test")
public class RabbitMQConfig {

    @Bean
    public Queue loggingQueue() {
        return QueueBuilder.durable(RABBIT_QUEUE_LOGGING).build();
    }

    @Bean
    public DirectExchange loggingExchange() {
        return ExchangeBuilder.directExchange(RABBIT_QUEUE_LOGGING_EXCHANGE).build();
    }

    @Bean
    Binding loggingBinding(Queue queueLogging, DirectExchange exchangeLogging, AmqpAdmin amqpAdmin){
        amqpAdmin.declareExchange(exchangeLogging);
        amqpAdmin.declareQueue(queueLogging);
        var binding = BindingBuilder.bind(queueLogging).to(exchangeLogging).with(RABBIT_QUEUE_LOGGING_ROUTER);
        amqpAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate template(ConnectionFactory connectionFactory) {
        final var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
