package kz.com.my_retro.amqp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {

    private String action;
    private String email;
    private boolean active;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime removed;

    public UserEvent() {}

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getRemoved() { return removed; }
    public void setRemoved(LocalDateTime removed) { this.removed = removed; }

    @Override
    public String toString() {
        return "UserEvent(action=" + action +
                ", email=" + email +
                ", active=" + active +
                ", removed=" + removed + ")";
    }
}
