package kz.com.java_component.users.security;

import kz.com.java_component.users.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationProvider authenticationProvider) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserRepository userRepository) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(
                new UserSecurityDetailsService(userRepository));
        return provider;
    }
}
