package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardPartDTO;
import com.mambocosmo.urzasoracle.entities.CardPart;

@Service
public class CardPartConverter implements GenericConverter<CardPart, CardPartDTO> {

    @Override
    public CardPart fromDToE(CardPartDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardPartDTO fromEToD(CardPart e) {
        CardPartDTO dto = new CardPartDTO();
        dto.setId(e.getId());
        dto.setComponent(e.getComponent());
        dto.setName(e.getName());
        dto.setType_line(e.getType_line());
        dto.setUri(e.getUri());
        return dto;
    }

}
