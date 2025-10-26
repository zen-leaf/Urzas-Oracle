package com.mambocosmo.urzasoracle.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class CardDeckPK implements Serializable {
    public CardDeckPK(String deckid, String cardid) {
        deck_id = deckid;
        card_id = cardid;
    }

    private String deck_id;

    private String card_id;

}
