package com.mambocosmo.urzasoracle.services;

import java.util.HashSet;
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

    private final CardExpansionSetService EXPANSIONSETSERVICE;

    @Override
    public Card construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(Card.class, fromData);
    }

    @Override
    public boolean save(Card fromEntity) {
        if (fromEntity.getArtistRef() == null) {
            fromEntity.setArtistRef(new HashSet<>());
        }
        if (fromEntity.getAll_parts() == null) {
            fromEntity.setAll_parts(new HashSet<>());
        }
        //EXPANSIONSETSERVICE.findById
        System.out.println(fromEntity.getExpansion().getName());

        try {
            getREPOSITORY().save(fromEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Card> getByName(String name) {
        return getREPOSITORY().findByName(name);
    }
    
    public List<Card> searchByName(String name) {
        return getREPOSITORY().findByNameContainingIgnoreCase(name);
    }
}
