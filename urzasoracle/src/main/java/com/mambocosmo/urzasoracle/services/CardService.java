package com.mambocosmo.urzasoracle.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.repositories.CardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = true)
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
        // EXPANSIONSETSERVICE.findById
        System.out.println("Card from expansion:" + fromEntity.getExpansion().getName());

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

    public List<Card> generateAllCardsFromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/json/singleCard.json"));

            List<Card> cardList = new ArrayList<>();
            Long myTimer = System.nanoTime();
            System.out.println("start");
            cardData.forEach(e -> {
                try {
                    Card myCard = null;
                    myCard = mapper.treeToValue(e, new TypeReference<Card>() {
                    });
                    myCard.setExpansion(
                            getEXPANSIONSETSERVICE().getEntityByID(UUID.fromString(e.get("set_id").asText())));

                    cardList.add(myCard);
                    // System.out.println(myCard.getName());
                } catch (JsonProcessingException | IllegalArgumentException e1) {
                    System.out.println("Error generating card!!! " + e1.getMessage());
                }
            });
            System.out.println("Completed card generation - Generated " + cardList.size() + " cards in "
                    + Double.valueOf((System.nanoTime() - myTimer)) / 1000000000 + " seconds");

            return cardList;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;

        }
    }

}
