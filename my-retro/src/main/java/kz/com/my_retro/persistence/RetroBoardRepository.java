package kz.com.my_retro.persistence;

import kz.com.my_retro.board.RetroBoard;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RetroBoardRepository extends ReactiveMongoRepository<RetroBoard, UUID> {

    @Query("{'id': ?0}")
    Mono<RetroBoard> findById(UUID id);

    @Query("{}, { cards: { $elemMatch: { _id: ?0 } } }")
    Mono<RetroBoard> findRetroBoardByIdAndCardId(UUID cardId);
}
