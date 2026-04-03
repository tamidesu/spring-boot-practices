package kz.com.my_retro.client;

import java.util.List;

public class User {

    private String email;
    private String name;
    private String password;
    private String gravatarUrl;
    private List<UserRole> userRole;
    private boolean active;

    public User() {}

    public User(String email, String name, String password,
                String gravatarUrl, List<UserRole> userRole, boolean active) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.gravatarUrl = gravatarUrl;
        this.userRole = userRole;
        this.active = active;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGravatarUrl() { return gravatarUrl; }
    public void setGravatarUrl(String gravatarUrl) { this.gravatarUrl = gravatarUrl; }

    public List<UserRole> getUserRole() { return userRole; }
    public void setUserRole(List<UserRole> userRole) { this.userRole = userRole; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
