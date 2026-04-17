package kz.com.java_component.users.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRabbitConfiguration {

    public static final String USERS_EXCHANGE      = "USERS";
    public static final String USERS_STATUS_QUEUE  = "USER_STATUS";
    public static final String USERS_REMOVED_QUEUE = "USER_REMOVED";
    public static final String USERS_ACTIVATED     = "users.activated";
    public static final String USERS_REMOVED       = "users.removed";

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var template = new RabbitTemplate(connectionFactory);
        template.setExchange(USERS_EXCHANGE);
        template.setMessageConverter(
                new Jackson2JsonMessageConverter(
                        new ObjectMapper().registerModule(new JavaTimeModule())));
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        var factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(
                new Jackson2JsonMessageConverter(
                        new ObjectMapper().registerModule(new JavaTimeModule())));
        return factory;
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(USERS_EXCHANGE);
    }

    @Bean
    public Queue userStatusQueue() {
        return new Queue(USERS_STATUS_QUEUE, true, false, false);
    }

    @Bean
    public Queue userRemovedQueue() {
        return new Queue(USERS_REMOVED_QUEUE, true, false, false);
    }

    @Bean
    public Binding userStatusBinding() {
        return new Binding(
                USERS_STATUS_QUEUE,
                Binding.DestinationType.QUEUE,
                USERS_EXCHANGE,
                USERS_ACTIVATED,
                null);
    }

    @Bean
    public Binding userRemovedBinding() {
        return new Binding(
                USERS_REMOVED_QUEUE,
                Binding.DestinationType.QUEUE,
                USERS_EXCHANGE,
                USERS_REMOVED,
                null);
    }
}
