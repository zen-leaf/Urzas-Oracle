package com.mambocosmo.urzasoracle.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.repositories.CardRepository;

@Service
public class CardService extends GenericService<Card, CardDTO, CardConverter, CardRepository> {

    @Override
    public Card construct(Map<String, String> fromData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'construct'");
    }
    // <E extends GenericEntity, D extends GenericDTO, C extends GenericConverter<E,
    // D>, R extends JpaRepository<E, IDType>
}
