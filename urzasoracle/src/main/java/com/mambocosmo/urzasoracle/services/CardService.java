package com.mambocosmo.urzasoracle.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.repositories.CardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper=true)
public class CardService extends GenericService<Card, CardDTO, CardConverter, CardRepository> {

    @Override
    public Card construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(Card.class, fromData);
    }

}
