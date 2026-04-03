package kz.com.my_retro.board;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document
public class RetroBoard {

    @Id
    private UUID id;
    private String name;
    private List<Card> cards;

    public RetroBoard() {}

    public RetroBoard(UUID id, String name, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
    }

    public void addCard(Card card) {
        ensureCardsExist().add(card);
    }

    public void addCards(Collection<Card> cards) {
        ensureCardsExist().addAll(cards);
    }

    private List<Card> ensureCardsExist() {
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        return this.cards;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }
}
