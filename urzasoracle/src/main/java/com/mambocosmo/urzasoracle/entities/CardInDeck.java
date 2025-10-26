package com.mambocosmo.urzasoracle.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity

@Data
@EqualsAndHashCode(callSuper = true)

public class CardInDeck extends GenericEntity {
    @EmbeddedId
    private CardDeckPK id;
    private String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("deck_id")
    @JoinColumn(name = "deck_id")
    private CardCollection deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("card_id")
    @JoinColumn(name = "card_id")
    private Card card;

    public CardInDeck(CardCollection deck, Card card, String quantity) {
        this.deck = deck;
        this.card = card;
        this.quantity = quantity;
        this.id = new CardDeckPK(deck.getId(), card.getId());
    }
}
