package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.converters.CardExpansionSetConverter;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.repositories.CardExpansionSetRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = true)
public class CardExpansionSetService extends
        GenericService<CardExpansionSet, CardExpansionSetDTO, CardExpansionSetConverter, CardExpansionSetRepository> {

    @Override
    public CardExpansionSet construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(CardExpansionSet.class, fromData);
    }

    public List<CardExpansionSet> getByName(String name) {
        return getREPOSITORY().findByName(name);
    }

}
