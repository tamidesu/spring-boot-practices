package kz.com.my_retro;

import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.persistence.RetroBoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mongoTest")
@DataMongoTest
public class RetroBoardMongoTests {

    @Autowired
    RetroBoardRepository retroBoardRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void saveRetroTest() {
        String namex = "Spring Boot 3 Retro";
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(UUID.randomUUID());
        retroBoard.setName(namex);

        RetroBoard retroBoardResult = retroBoardRepository.insert(retroBoard).block();

        assertThat(retroBoardResult).isNotNull();
        assertThat(retroBoardResult.getId()).isInstanceOf(UUID.class);
        assertThat(retroBoardResult.getName()).isEqualTo(namex);
    }

    @Test
    void findRetroBoardById() {
        RetroBoard retroBoard = new RetroBoard();
        retroBoard.setId(UUID.randomUUID());
        retroBoard.setName("Migration Retro");

        RetroBoard retroBoardResult = retroBoardRepository.insert(retroBoard).block();
        assertThat(retroBoardResult).isNotNull();
        assertThat(retroBoardResult.getId()).isInstanceOf(UUID.class);

        StepVerifier
                .create(retroBoardRepository.findById(retroBoardResult.getId()))
                .assertNext(retro -> {
                    assertThat(retro).isNotNull();
                    assertThat(retro).isInstanceOf(RetroBoard.class);
                    assertThat(retro.getName()).isEqualTo("Migration Retro");
                })
                .expectComplete()
                .verify();
    }
}
