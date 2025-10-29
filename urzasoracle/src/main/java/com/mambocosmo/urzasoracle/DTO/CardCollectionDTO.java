package com.mambocosmo.urzasoracle.DTO;

import java.util.List;
import java.util.UUID;

import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.misc.enums.Format;

import lombok.Data;

@Data
public class CardCollectionDTO implements GenericDTO {

private UUID id;
    private UrzaUser owner;
    private String name;
    private String description;
    private List<Format> legalIn;
    private List<CardInDeck> cardList;

}
