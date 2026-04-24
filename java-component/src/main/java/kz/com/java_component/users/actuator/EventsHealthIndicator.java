package kz.com.java_component.users.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class EventsHealthIndicator implements HealthIndicator {

    private final LogEventEndpoint logEventEndpoint;

    public EventsHealthIndicator(LogEventEndpoint logEventEndpoint) {
        this.logEventEndpoint = logEventEndpoint;
    }

    @Override
    public Health health() {
        if (check()) {
            return Health.up().build();
        } else {
            return Health.status(
                    new Status("EVENTS-DOWN", "Events are turned off!")
            ).build();
        }
    }

    private boolean check() {
        return logEventEndpoint.isEnable();
    }
}
