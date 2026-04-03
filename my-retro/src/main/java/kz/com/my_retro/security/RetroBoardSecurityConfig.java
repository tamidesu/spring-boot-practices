package kz.com.my_retro.security;

import kz.com.my_retro.client.User;
import kz.com.my_retro.client.UserClient;
import kz.com.my_retro.client.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RetroBoardSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager reactiveAuthenticationManager,
            CorsConfigurationSource corsConfigurationSource) throws Exception {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "/retros/**")
                        .hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/retros/**")
                        .hasRole("ADMIN")
                        .pathMatchers("/retros/**")
                        .hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/", "/webjars/**")
                        .permitAll()
                )
                .authenticationManager(reactiveAuthenticationManager)
                .formLogin(form -> form
                        .authenticationSuccessHandler(
                                serverAuthenticationSuccessHandler()))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public ServerAuthenticationSuccessHandler serverAuthenticationSuccessHandler() {
        return (webFilterExchange, authentication) ->
                webFilterExchange.getExchange().getSession()
                        .flatMap(session -> {
                            User user = (User) authentication.getDetails();

                            String roles = authentication.getAuthorities()
                                    .stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .map(role -> role.replace("ROLE_", ""))
                                    .collect(Collectors.joining(","));

                            String body = """
                            {
                                "email": "%s",
                                "name": "%s",
                                "password": "%s",
                                "userRole": "%s",
                                "gravatarUrl": "%s",
                                "active": %s
                            }
                            """.formatted(
                                    user.getEmail(),
                                    user.getName(),
                                    user.getPassword(),
                                    roles,
                                    user.getGravatarUrl(),
                                    true);

                            var response = webFilterExchange.getExchange().getResponse();
                            response.setStatusCode(HttpStatusCode.valueOf(200));
                            response.getHeaders().add("Content-Type", "application/json");
                            response.getHeaders().add("X-MYRETRO",
                                    "SESSION=" + session.getId()
                                            + "; Path=/; HttpOnly; SameSite=Lax");

                            DataBuffer dataBuffer = response.bufferFactory()
                                    .wrap(body.getBytes());

                            return response.writeAndFlushWith(
                                    Flux.just(dataBuffer).windowUntilChanged());
                        });
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of(
                "x-ijt", "Set-Cookie", "Cookie", "Content-Type",
                "X-MYRETRO", "Allow", "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin", "X-Requested-With", "Accept",
                "Accept-Encoding", "Accept-Language",
                "Host", "Referer", "Connection", "User-Agent"));
        configuration.setExposedHeaders(List.of(
                "x-ijt", "Set-Cookie", "Cookie", "Content-Type",
                "X-MYRETRO", "Allow", "Authorization",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Methods",
                "Access-Control-Expose-Headers",
                "Access-Control-Max-Age",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Origin", "X-Requested-With", "Accept",
                "Accept-Encoding", "Accept-Language",
                "Host", "Referer", "Connection", "User-Agent"));
        configuration.setAllowedOriginPatterns(
                List.of("http://localhost:*"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            UserClient userClient) {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            return userClient.getUserInfo(username)
                    .flatMap(user -> {
                        if (user.getPassword().equals(password)) {
                            List<GrantedAuthority> authorities =
                                    user.getUserRole().stream()
                                            .map(UserRole::name)
                                            .map(name -> "ROLE_" + name)
                                            .map(SimpleGrantedAuthority::new)
                                            .collect(Collectors.toList());

                            var token =
                                    new UsernamePasswordAuthenticationToken(
                                            username, password, authorities);
                            token.setDetails(user);

                            return Mono.just(
                                    (org.springframework.security.core
                                            .Authentication) token);
                        } else {
                            return Mono.error(
                                    new BadCredentialsException(
                                            "Invalid username or password"));
                        }
                    });
        };
    }
}
