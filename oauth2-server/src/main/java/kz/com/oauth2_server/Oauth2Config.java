package kz.com.oauth2_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class Oauth2Config {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(
            PasswordEncoder passwordEncoder) {

        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("users.read", "users.write")
                .build();

        var manager = User.builder()
                .username("manager@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .authorities("users.read", "users.write")
                .build();

        var user = User.builder()
                .username("user@email.com")
                .password(passwordEncoder.encode("aw2s0meR!"))
                .authorities("users.read")
                .build();

        return new InMemoryUserDetailsManager(manager, user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
