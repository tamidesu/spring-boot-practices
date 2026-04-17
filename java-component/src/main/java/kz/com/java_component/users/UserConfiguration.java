package kz.com.java_component.users;

import kz.com.java_component.users.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class UserConfiguration {

    @Bean
    @Profile("default")
    public CommandLineRunner init(UserService userService) {
        return args -> {
            userService.saveUpdateUser(new User(
                    "ximena@email.com", "Ximena",
                    "https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar",
                    "aw2s0me", List.of(UserRole.USER), true
            ));
            userService.saveUpdateUser(new User(
                    "norma@email.com", "Norma",
                    "https://www.gravatar.com/avatar/f07f7e553264c9710105edebe6c465e7?d=wavatar",
                    "aw2s0me", List.of(UserRole.USER, UserRole.ADMIN), true
            ));
        };
    }

    @Bean
    @Profile("mockMvc")
    public CommandLineRunner initMockMvc(UserService userService) {
        return args -> {};
    }
}
