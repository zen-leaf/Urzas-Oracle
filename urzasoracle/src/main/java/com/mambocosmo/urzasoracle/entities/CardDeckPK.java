package com.mambocosmo.urzasoracle.entities;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class CardDeckPK implements Serializable {
    public CardDeckPK(UUID deckid, UUID cardid) {
        deck_id = deckid;
        card_id = cardid;
    }

    private UUID deck_id;

    private UUID card_id;

}
