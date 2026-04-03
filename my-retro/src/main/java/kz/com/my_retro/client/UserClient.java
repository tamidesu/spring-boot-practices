package kz.com.my_retro.client;

import kz.com.my_retro.config.MyRetroProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserClient {

    private final WebClient webClient;

    public UserClient(WebClient.Builder webClientBuilder,
                      MyRetroProperties props) {
        this.webClient = webClientBuilder
                .baseUrl(props.getUsers().getServer())
                .defaultHeaders(headers ->
                        headers.setBasicAuth(
                                props.getUsers().getUsername(),
                                props.getUsers().getPassword()
                        )
                )
                .build();
    }

    public Mono<User> getUserInfo(String email) {
        return webClient.get()
                .uri("/users/{email}", email)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<String> getUserGravatar(String email) {
        return webClient.get()
                .uri("/users/{email}/gravatar", email)
                .retrieve()
                .bodyToMono(String.class);
    }
}
