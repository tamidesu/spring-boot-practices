package kz.com.my_retro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "service")
public class MyRetroProperties {

    private UsersConfiguration users;

    public UsersConfiguration getUsers() { return users; }
    public void setUsers(UsersConfiguration users) { this.users = users; }
}
