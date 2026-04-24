package kz.com.java_component.users.events;

import kz.com.java_component.users.actuator.LogEventEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserLogs {

    private static final Logger LOG = LoggerFactory.getLogger(UserLogs.class);

    private final LogEventEndpoint logEventEndpoint;

    public UserLogs(LogEventEndpoint logEventEndpoint) {
        this.logEventEndpoint = logEventEndpoint;
    }

    @Async
    @EventListener
    public void userActiveStatusEventHandler(UserActivatedEvent event) {
        if (logEventEndpoint.isEnable()) {
            LOG.info("{} User {} active status: {} {}",
                    logEventEndpoint.config().getPrefix(),
                    event.getEmail(),
                    event.getActive(),
                    logEventEndpoint.config().getPostfix());
        } else {
            LOG.info("User {} active status: {}", event.getEmail(), event.getActive());
        }
    }

    @Async
    @EventListener
    public void userDeletedEventHandler(UserRemovedEvent event) {
        if (logEventEndpoint.isEnable()) {
            LOG.info("{} User {} DELETED at {} {}",
                    logEventEndpoint.config().getPrefix(),
                    event.getEmail(),
                    event.getRemoved(),
                    logEventEndpoint.config().getPostfix());
        } else {
            LOG.info("User {} DELETED at {}", event.getEmail(), event.getRemoved());
        }
    }

    @EventListener
    public void on(AuditApplicationEvent event) {
        LOG.info("Audit Event: {}", event);
    }
}
