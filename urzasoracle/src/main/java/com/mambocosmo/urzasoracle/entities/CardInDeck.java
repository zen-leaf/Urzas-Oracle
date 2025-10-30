package com.mambocosmo.urzasoracle.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CardInDeck extends GenericEntity {
    
    @EmbeddedId
    private CardDeckPK id;
    
    private String quantity;
    
    public CardInDeck(CardCollection deck, Card card, String quantity) {
        this.quantity = quantity;
        this.id = new CardDeckPK(deck, card);
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
    // @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @MapsId("card_id")
    // @JoinColumn(name = "card_id")
    // private Card card;


}
