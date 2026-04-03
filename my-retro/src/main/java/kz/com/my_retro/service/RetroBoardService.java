package kz.com.my_retro.service;

import kz.com.my_retro.board.Card;
import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.persistence.RetroBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class RetroBoardService {

    @Autowired
    private RetroBoardRepository retroBoardRepository;

    public Mono<RetroBoard> save(RetroBoard domain) {
        return retroBoardRepository.save(domain);
    }

    public Mono<RetroBoard> findById(UUID uuid) {
        return retroBoardRepository.findById(uuid);
    }

    public Flux<RetroBoard> findAll() {
        return retroBoardRepository.findAll();
    }

    public Mono<Void> delete(UUID uuid) {
        return retroBoardRepository.deleteById(uuid);
    }

    public Flux<Card> findAllCardsFromRetroBoard(UUID uuid) {
        return findById(uuid).flatMapIterable(RetroBoard::getCards);
    }

    public Mono<Card> addCardToRetroBoard(UUID uuid, Card card) {
        return findById(uuid).flatMap(retroBoard -> {
            if (card.getId() == null) {
                card.setId(UUID.randomUUID());
            }
            retroBoard.getCards().add(card);
            return save(retroBoard).thenReturn(card);
        });
    }

    public Mono<Card> findCardByUUID(UUID uuidCard) {
        return retroBoardRepository.findRetroBoardByIdAndCardId(uuidCard)
                .flatMapIterable(RetroBoard::getCards)
                .filter(card -> uuidCard.equals(card.getId()))
                .next();
    }

    public Mono<Void> removeCardByUUID(UUID uuid, UUID cardUUID) {
        findById(uuid)
                .doOnNext(retroBoard ->
                        retroBoard.getCards().removeIf(card -> cardUUID.equals(card.getId()))
                )
                .flatMap(this::save)
                .subscribe();
        return Mono.empty();
    }
}
