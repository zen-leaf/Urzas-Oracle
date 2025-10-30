package com.mambocosmo.urzasoracle.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class CardInDeckDTO {
    private UUID cardId;
    private String cardName;
    private String typeline;
    private String quantity;
    private String imageUrl;
}
