package com.mambocosmo.urzasoracle.services;

import java.util.List;
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
@EqualsAndHashCode
public class CardService extends GenericService<Card, CardDTO, CardConverter, CardRepository> {

    @Override
    public Card construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(Card.class, fromData);
    }

    public List<Card> getByName(String name) {
        return getREPOSITORY().findByName(name);
    }
    
    public List<Card> searchByName(String name) {
        return getREPOSITORY().findByNameContainingIgnoreCase(name);
    }
}
