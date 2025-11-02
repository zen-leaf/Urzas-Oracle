package com.mambocosmo.urzasoracle.DTO;

import java.util.UUID;
import lombok.Data;

@Data
public class CardInDeckDTO {
    private UUID cardId;
    private CardDTO refCard;
    private String cardName;
    private String typeline;
    private Integer quantity;
    private String imageUrl;
}
