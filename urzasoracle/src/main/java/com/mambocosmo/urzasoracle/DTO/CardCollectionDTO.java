package com.mambocosmo.urzasoracle.DTO;

import java.util.List;
import java.util.UUID;

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
    private Format mainDeckFormat;
    private Integer totalCards;
    private List<CardInDeckDTO> previewCards;
    // private String previewimg;
    private CardDTO bannerCard;
    private CardDTO commander;
}
