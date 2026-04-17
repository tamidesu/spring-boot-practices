package kz.com.java_component.users.events;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class UserRemovedEvent {

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime removed;

    private String action = "REMOVED";

    public UserRemovedEvent() {}

    public UserRemovedEvent(String email, LocalDateTime removed) {
        this.email = email;
        this.removed = removed;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getRemoved() { return removed; }
    public void setRemoved(LocalDateTime removed) { this.removed = removed; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    @Override
    public String toString() {
        return "UserRemovedEvent(email=" + email +
                ", removed=" + removed +
                ", action=" + action + ")";
    }
}
