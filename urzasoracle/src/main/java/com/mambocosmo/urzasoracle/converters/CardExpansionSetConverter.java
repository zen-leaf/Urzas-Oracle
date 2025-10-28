package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;

@Service
public class CardExpansionSetConverter implements GenericConverter<CardExpansionSet, CardExpansionSetDTO> {

    @Override
    public CardExpansionSet fromDToE(CardExpansionSetDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public CardExpansionSetDTO fromEToD(CardExpansionSet e) {
        CardExpansionSetDTO dto = new CardExpansionSetDTO();
        dto.setCode(e.getCode());
        dto.setName(e.getName());
        dto.setRelease_date(e.getReleased_at());
        dto.setIcon_svg_uri(e.getIcon_svg_uri());
        return dto;
    }

}
