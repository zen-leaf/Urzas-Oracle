package com.mambocosmo.urzasoracle.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardInDeck extends GenericEntity {

    @EmbeddedId
    private CardDeckPK id;

    private Integer quantity;

    public CardInDeck(CardCollection deck, Card card, Integer quantity) {
        this.quantity = quantity;
        this.id = new CardDeckPK(deck, card);
    }

    public Card getCard() {
        return getId().getCard();
    }

    // public CardInDeck(){}

    // @EqualsAndHashCode.Exclude
    // @ToString.Exclude
    // @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("deck_id")
    // @JoinColumn(name = "deck_id")
    // private CardCollection deck;

    // @EqualsAndHashCode.Exclude
    // @ToString.Exclude
    // @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
    // CascadeType.MERGE })
    // @MapsId("card_id")
    // @JoinColumn(name = "card_id")
    // private Card card;

}
