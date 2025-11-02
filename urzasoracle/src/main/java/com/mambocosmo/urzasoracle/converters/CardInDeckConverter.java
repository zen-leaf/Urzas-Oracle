package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Component;
import com.mambocosmo.urzasoracle.DTO.CardInDeckDTO;
import com.mambocosmo.urzasoracle.entities.CardInDeck;

import lombok.Data;

@Data
@Component
public class CardInDeckConverter {

    private CardConverter CARDCONVERTER;
    
    public CardInDeckDTO fromEToD(CardInDeck entity) {
        if (entity == null) return null;
        
        CardInDeckDTO dto = new CardInDeckDTO();
        dto.setCardId(entity.getId().getCard().getId());
        dto.setCardName(entity.getId().getCard().getName());
        dto.setTypeline(entity.getId().getCard().getType_line());
        dto.setQuantity(entity.getQuantity());
        dto.setRefCard(getCARDCONVERTER().fromEToD(entity.getId().getCard()));
        String imageUrl = null;
        if (entity.getId().getCard().getImage_uris() != null) {
            imageUrl = entity.getId().getCard().getImage_uris().get("normal");
        }
        dto.setImageUrl(imageUrl);
        
        return dto;
    }
}
