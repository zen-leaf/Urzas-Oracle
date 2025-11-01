package com.mambocosmo.urzasoracle.converters;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;

@Service
public class CardExpansionSetConverter implements GenericConverter<CardExpansionSet, CardExpansionSetDTO> {

    @Override
    public CardExpansionSet fromDToE(CardExpansionSetDTO dto) {
        CardExpansionSet ces = new CardExpansionSet();
        ces.setCode(dto.getCode());
        ces.setName(dto.getName());
        ces.setReleased_at(Date.valueOf(dto.getRelease_date()));
        ces.setIcon_svg_uri(dto.getIcon_svg_uri());
        return ces;
    }

    @Override
    public CardExpansionSetDTO fromEToD(CardExpansionSet e) {
        CardExpansionSetDTO dto = new CardExpansionSetDTO();
        dto.setCode(e.getCode());
        dto.setName(e.getName());
        dto.setRelease_date(String.valueOf(e.getReleased_at()));
        dto.setIcon_svg_uri(e.getIcon_svg_uri());
        return dto;
    }

}
