package kz.com.java_component.users.actuator;

public class LogEventConfig {

    private Boolean enabled = false;
    private String prefix = ">> ";
    private String postfix = " <<";

    public LogEventConfig() {}

    public LogEventConfig(Boolean enabled, String prefix, String postfix) {
        this.enabled = enabled;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public String getPostfix() { return postfix; }
    public void setPostfix(String postfix) { this.postfix = postfix; }
}
