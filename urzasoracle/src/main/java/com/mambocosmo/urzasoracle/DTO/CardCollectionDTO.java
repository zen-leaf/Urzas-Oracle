package com.mambocosmo.urzasoracle.DTO;

import java.util.Map;
import java.util.List;
import java.util.UUID;

import javax.smartcardio.Card;

import com.mambocosmo.urzasoracle.misc.enums.Format;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CardCollectionDTO implements GenericDTO {
    private UUID id;
    private String owner;
    private String name;
    private String description;
    private String mainDeckFormat;
    private Map<CardDTO,String> cardInDeck;
    private Integer totalCards;
    
    // private String previewimg;
    private List<CardInDeckDTO> previewCards;
    private CardDTO bannerCard;
    private CardDTO commander;
    
    //TODO is legal in used?
    private Map<String,String> legalIn;
}
