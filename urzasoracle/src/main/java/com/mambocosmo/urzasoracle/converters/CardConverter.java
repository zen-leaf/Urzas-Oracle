package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;

@Service
public class CardConverter implements GenericConverter<Card, CardDTO> {

    @Override
    public Card fromDToE(CardDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardDTO fromEToD(Card e) {
        CardDTO dto = new CardDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setImages(e.getImage_uris());
        return dto;
    }

}
