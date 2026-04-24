package kz.com.my_retro.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserClientConfig {

    @Bean
    public WebClient webClient(
            @Value("${users.app.url}") String baseUrl,
            @Value("${users.app.username}") String username,
            @Value("${users.app.password}") String password) {
        return WebClient.builder()
                .defaultHeaders(header ->
                        header.setBasicAuth(username, password))
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public UserClient userClient(WebClient webClient) {
        HttpServiceProxyFactory factory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(webClient))
                        .build();
        return factory.createClient(UserClient.class);
    }
}
