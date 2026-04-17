package kz.com.java_component.users.events;

public class UserActivatedEvent {

    private String email;
    private Boolean active;
    private String action = "ACTIVATION_STATUS";

    public UserActivatedEvent() {}

    public UserActivatedEvent(String email, Boolean active) {
        this.email = email;
        this.active = active;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    @Override
    public String toString() {
        return "UserActivatedEvent(email=" + email +
                ", active=" + active +
                ", action=" + action + ")";
    }
}
