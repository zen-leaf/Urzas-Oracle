package com.mambocosmo.urzasoracle.DTO;

import java.util.Map;
import java.util.UUID;

import javax.smartcardio.Card;

import com.mambocosmo.urzasoracle.misc.enums.Format;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardCollectionDTO implements GenericDTO {
    private UUID id;
    private UrzaUserDTO owner;
    private String name;
    private String description;
    private String mainDeckFormat;
    private Map<String,String> legalIn;
    private Map<CardDTO,String> cardInDeck;
}
