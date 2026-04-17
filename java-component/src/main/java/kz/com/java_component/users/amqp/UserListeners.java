package kz.com.java_component.users.amqp;

import kz.com.java_component.users.events.UserActivatedEvent;
import kz.com.java_component.users.events.UserRemovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserListeners {

    private static final Logger LOG =
            LoggerFactory.getLogger(UserListeners.class);

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = UserRabbitConfiguration.USERS_STATUS_QUEUE,
                            durable = "true",
                            autoDelete = "false"
                    ),
                    exchange = @Exchange(
                            name = UserRabbitConfiguration.USERS_EXCHANGE,
                            type = "topic"
                    ),
                    key = UserRabbitConfiguration.USERS_ACTIVATED
            )
    )
    public void userStatusEventProcessing(UserActivatedEvent activatedEvent) {
        LOG.info("[AMQP - Event] Activated Event Received: {}", activatedEvent);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = UserRabbitConfiguration.USERS_REMOVED_QUEUE,
                            durable = "true",
                            autoDelete = "false"
                    ),
                    exchange = @Exchange(
                            name = UserRabbitConfiguration.USERS_EXCHANGE,
                            type = "topic"
                    ),
                    key = UserRabbitConfiguration.USERS_REMOVED
            )
    )
    public void userRemovedEventProcessing(UserRemovedEvent removedEvent) {
        LOG.info("[AMQP - Event] Removed Event Received: {}", removedEvent);
    }
}
