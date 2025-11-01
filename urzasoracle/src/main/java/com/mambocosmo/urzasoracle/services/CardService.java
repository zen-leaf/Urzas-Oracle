package com.mambocosmo.urzasoracle.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.misc.Utils.QueryParser;
import com.mambocosmo.urzasoracle.misc.Utils.SearchCriteria;
import com.mambocosmo.urzasoracle.repositories.CardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = false)
public class CardService extends GenericService<Card, CardDTO, CardConverter, CardRepository> {

    private final CardExpansionSetService EXPANSIONSETSERVICE;

    @Override
    public Card construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(Card.class, fromData);
    }

    @Override
    public boolean save(Card fromEntity) {
        if (fromEntity.getArtistRef() == null) {
            fromEntity.setArtistRef(new ArrayList<>());
        }
        if (fromEntity.getAll_parts() == null) {
            fromEntity.setAll_parts(new ArrayList<>());
        }

        if (fromEntity.getCard_faces() == null) {
            fromEntity.setCard_faces(new ArrayList<>());
        }

        // System.out.println("Card from expansion:" +
        // fromEntity.getExpansion().getName());

        try {
            getREPOSITORY().save(fromEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<CardDTO> getByName(String name) {
        List<CardDTO> out = getREPOSITORY().findByNameContainingIgnoreCase(name).stream().map(e -> {
            return getCONVERTER().fromEToD(e);
        }).toList();
        return out;
    }

    public Page<CardDTO> getByNamePaged(String name, Integer numeroPagina, Integer dimensione) {
        Pageable pageable = PageRequest.of(numeroPagina, dimensione);
        Page<Card> page = getREPOSITORY().findByNameContainingIgnoreCase(name, pageable);

        return page.map(card -> getCONVERTER().fromEToD(card));
    }

    // Prendi una singola carta per ID (UUID)
    public CardDTO getCardById(UUID id) {
        return getCONVERTER().fromEToD(getREPOSITORY().findById(id).orElse(null));
    }

    public List<Card> generateAllCardsFromJSON() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/json/test.json"));
            List<Card> cardList = new ArrayList<>();

            Long myTimer = System.nanoTime();
            System.out.println("start");
            cardData.forEach(e -> {
                try {
                    Card myCard = new Card();
                    myCard = mapper.treeToValue(e, new TypeReference<Card>() {
                    });
                    myCard.setExpansion(
                            getEXPANSIONSETSERVICE().getEntityByID(UUID.fromString(e.get("set_id").asText())));
                    // System.out.println(e.toString());
                    // cardList.add(myCard);
                    // System.out.println(
                    // myCard.getName() + " - " + myCard.getId() + " - \n" + "Artist:\n" +
                    // myCard.getArtistRef());
                    save(myCard);
                    System.out.println("Saved card: " + myCard.getName());

                } catch (JsonProcessingException | IllegalArgumentException e1) {
                    System.out.println("Error generating card!!! " + e1.getMessage());
                }
            });
            System.out.println("Completed card generation - Generated " + cardList.size() + " cards in "
                    + Double.valueOf((System.nanoTime() - myTimer)) / 1000000000 + " seconds");

            return cardList;
        } catch (IOException e) {
            return null;
        }
    }

    public Map<String, Integer> getUniqueCardFaces() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/json/uniques.json"));
            Map<String, Integer> uniqueFaces = new java.util.HashMap<>();
            Long myTimer = System.nanoTime();
            System.out.println("start");
            cardData.forEach(e -> {
                try {
                    if (e.get("card_faces") == null) {
                        return;
                    }
                    e.get("card_faces").forEach(face -> {
                        String faceName = face.get("name").asText();
                        System.out.println(faceName);
                        if (uniqueFaces.containsKey(faceName)) {
                            uniqueFaces.put(faceName, uniqueFaces.get(faceName) + 1);
                        } else {
                            uniqueFaces.put(faceName, 1);
                        }
                    });
                } catch (IllegalArgumentException e1) {
                    System.out.println("Error generating card!!! " + e1.getMessage());
                }
            });
            System.out.println("Completed card generation - Generated cards in "
                    + Double.valueOf((System.nanoTime() - myTimer)) / 1000000000 + " seconds");

            return uniqueFaces;
        } catch (IOException e) {
            return null;
        }
    }

    public Page<CardDTO> getAllPaged(int numeroPagina, int dimensione) {
        Pageable pageable = PageRequest.of(numeroPagina, dimensione);
        Page<Card> page = getREPOSITORY().findAll(pageable);

        return page.map(card -> getCONVERTER().fromEToD(card));
    }

    public Page<CardDTO> getRandomPaged(int size) {
        Pageable pageable = PageRequest.of(0, size);
        Page<Card> temp = getREPOSITORY().findRandomSubSet(size, pageable);
        return temp.map(card -> getCONVERTER().fromEToD(card));

    }

    public List<Card> getAllCardEntities() {
        List<Card> page = getREPOSITORY().findAll();

        return page;
    }

    public Page<CardDTO> searchByQueryPaged(String q, int page, int size) {
        Specification<Card> spec = Specification.unrestricted();
        if (!q.toLowerCase().contains("nonplayable:")) {
            System.out.println("adding default artwork exclusion");
            q += " nonplayable:exclude";
            System.out.println(q);
        }

        for (SearchCriteria query : SearchCriteria.StringToCriteria(q)) {
            QueryParser cardQ = new QueryParser(query);
            spec = spec.and(cardQ);
        }
        Pageable pageable = PageRequest.of(page, size);
        return getREPOSITORY().findAll(spec, pageable).map(e -> getCONVERTER().fromEToD(e));

    }

}
