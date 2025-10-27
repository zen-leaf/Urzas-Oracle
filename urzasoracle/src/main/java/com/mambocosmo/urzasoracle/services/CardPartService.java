package com.mambocosmo.urzasoracle.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardPartDTO;
import com.mambocosmo.urzasoracle.converters.CardPartConverter;
import com.mambocosmo.urzasoracle.entities.CardPart;
import com.mambocosmo.urzasoracle.repositories.CardPartRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper=true)
public class CardPartService extends GenericService<CardPart, CardPartDTO, CardPartConverter, CardPartRepository> {
    @Override
    public CardPart construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(CardPart.class, fromData);
    }

}
