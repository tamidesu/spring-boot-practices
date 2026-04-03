package kz.com.my_retro.board;

import java.util.UUID;

public class Card {
    private UUID id;
    private String comment;
    private CardType cardType;

    public Card() {}

    public Card(UUID id, String comment, CardType cardType) {
        this.id = id;
        this.comment = comment;
        this.cardType = cardType;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public CardType getCardType() { return cardType; }
    public void setCardType(CardType cardType) { this.cardType = cardType; }
}
