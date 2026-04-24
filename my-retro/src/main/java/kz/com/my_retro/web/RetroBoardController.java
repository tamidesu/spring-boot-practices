package kz.com.my_retro.web;

import kz.com.my_retro.board.Card;
import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.client.User;
import kz.com.my_retro.service.RetroBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/retros")
public class RetroBoardController {

    @Autowired
    private RetroBoardService retroBoardService;

    @GetMapping
    public Flux<RetroBoard> allRetroBoards() {
        return retroBoardService.findAll();
    }

    @PostMapping
    public Mono<RetroBoard> saveRetroBoard(@RequestBody RetroBoard retroBoard) {
        return retroBoardService.save(retroBoard);
    }

    @DeleteMapping("/{uuid}")                          // ← was missing
    public Mono<Void> deleteRetroBoard(@PathVariable UUID uuid) {
        return retroBoardService.delete(uuid);
    }

    @GetMapping("/{uuid}")
    public Mono<RetroBoard> findRetroBoardById(@PathVariable UUID uuid) {
        return retroBoardService.findById(uuid);
    }

    @GetMapping("/{uuid}/cards")
    public Flux<Card> getAllCardsFromBoard(@PathVariable UUID uuid) {
        return retroBoardService.findAllCardsFromRetroBoard(uuid);
    }

    @PutMapping("/{uuid}/cards")
    public Mono<Card> addCardToRetroBoard(@PathVariable UUID uuid, @RequestBody Card card) {
        return retroBoardService.addCardToRetroBoard(uuid, card);
    }

    @GetMapping("/cards/{uuidCard}")
    public Mono<Card> getCardByUUID(@PathVariable UUID uuidCard) {
        return retroBoardService.findCardByUUID(uuidCard);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}/cards/{uuidCard}")
    public Mono<Void> deleteCardFromRetroBoard(@PathVariable UUID uuid,
                                               @PathVariable UUID uuidCard) {
        return retroBoardService.removeCardByUUID(uuid, uuidCard);
    }

    @GetMapping("/users")
    public Flux<User> getAllUsers() {
        return retroBoardService.getAllUsers();
    }
}
