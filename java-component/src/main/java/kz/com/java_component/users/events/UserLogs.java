package kz.com.java_component.users.events;

import kz.com.java_component.users.amqp.UserRabbitConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserLogs {

    private static final Logger LOG = LoggerFactory.getLogger(UserLogs.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener
    public void userActiveStatusEventHandler(UserActivatedEvent event) {
        rabbitTemplate.convertAndSend(
                UserRabbitConfiguration.USERS_ACTIVATED, event);
        LOG.info("User {} active status: {}", event.getEmail(), event.getActive());
    }

    @Async
    @EventListener
    public void userDeletedEventHandler(UserRemovedEvent event) {
        rabbitTemplate.convertAndSend(
                UserRabbitConfiguration.USERS_REMOVED, event);
        LOG.info("User {} DELETED at {}", event.getEmail(), event.getRemoved());
    }
}
