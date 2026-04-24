package kz.com.my_retro.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.observation.annotation.Observed;
import kz.com.my_retro.board.Card;
import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.client.User;
import kz.com.my_retro.client.UserClient;
import kz.com.my_retro.events.RetroBoardEvent;
import kz.com.my_retro.events.RetroBoardEventAction;
import kz.com.my_retro.persistence.RetroBoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Observed(name = "retro-board-service",
        contextualName = "retroBoardAndCardService")
public class RetroBoardService {

    private static final Logger LOG =
            LoggerFactory.getLogger(RetroBoardService.class);

    @Autowired
    private RetroBoardRepository retroBoardRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private Counter retroBoardCounter;

    @Autowired
    private UserClient userClient;

    public Mono<RetroBoard> save(RetroBoard domain) {
        return retroBoardRepository.save(domain)
                .doOnSuccess(saved -> {
                    eventPublisher.publishEvent(new RetroBoardEvent(
                            saved.getId(),
                            RetroBoardEventAction.CHANGED,
                            LocalDateTime.now()));
                    retroBoardCounter.increment();
                });
    }

    public Mono<RetroBoard> findById(UUID uuid) {
        return retroBoardRepository.findById(uuid);
    }

    public Flux<RetroBoard> findAll() {
        LOG.info("Getting all retro boards");
        return retroBoardRepository.findAll();
    }

    public Mono<Void> delete(UUID uuid) {
        return retroBoardRepository.deleteById(uuid)
                .doOnSuccess(v -> eventPublisher.publishEvent(
                        new RetroBoardEvent(
                                uuid,
                                RetroBoardEventAction.DELETED,
                                LocalDateTime.now())));
    }

    public Flux<Card> findAllCardsFromRetroBoard(UUID uuid) {
        return findById(uuid).flatMapIterable(RetroBoard::getCards);
    }

    public Mono<Card> addCardToRetroBoard(UUID uuid, Card card) {
        return findById(uuid).flatMap(retroBoard -> {
            if (card.getId() == null) card.setId(UUID.randomUUID());
            retroBoard.getCards().add(card);
            return save(retroBoard).thenReturn(card);
        });
    }

    public Mono<Card> findCardByUUID(UUID uuidCard) {
        return retroBoardRepository
                .findRetroBoardByIdAndCardId(uuidCard)
                .flatMapIterable(RetroBoard::getCards)
                .filter(card -> uuidCard.equals(card.getId()))
                .next();
    }

    public Mono<Void> removeCardByUUID(UUID uuid, UUID cardUUID) {
        findById(uuid)
                .doOnNext(retroBoard ->
                        retroBoard.getCards().removeIf(
                                card -> cardUUID.equals(card.getId())))
                .flatMap(this::save)
                .subscribe();
        return Mono.empty();
    }

    public Flux<User> getAllUsers() {
        LOG.info("Getting all users");
        return userClient.getAllUsers()
                .doOnNext(user -> LOG.info("User: {}", user))
                .doOnComplete(() -> LOG.info("Completed"))
                .doOnError(error -> LOG.error("Error: {}", error.toString()));
    }
}
