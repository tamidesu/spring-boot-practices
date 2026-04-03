package kz.com.my_retro.config;

import kz.com.my_retro.board.Card;
import kz.com.my_retro.board.CardType;
import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.service.RetroBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EnableConfigurationProperties(MyRetroProperties.class)
@Configuration
public class MyRetroConfiguration {

    private static final Logger LOG =
            LoggerFactory.getLogger(MyRetroConfiguration.class);

    @Bean
    public ApplicationListener<ApplicationReadyEvent> ready(
            RetroBoardService retroBoardService) {
        return event -> {
            LOG.info("Application Ready Event");
            UUID retroBoardId = UUID.fromString(
                    "9dc9b71b-a07e-418b-b972-40225449aff2");
            retroBoardService.save(
                    new RetroBoard(
                            retroBoardId,
                            "Spring Boot Conference",
                            new ArrayList<>(List.of(
                                    new Card(UUID.fromString(
                                            "bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
                                            "Spring Boot Rocks!", CardType.HAPPY),
                                    new Card(UUID.randomUUID(),
                                            "Meet everyone in person", CardType.HAPPY),
                                    new Card(UUID.randomUUID(),
                                            "When is the next one?", CardType.MEH),
                                    new Card(UUID.randomUUID(),
                                            "Not enough time to talk to everyone",
                                            CardType.SAD)
                            ))
                    )
            ).subscribe();
        };
    }
}
