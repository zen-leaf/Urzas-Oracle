package com.mambocosmo.urzasoracle.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
import com.mambocosmo.urzasoracle.misc.Utils.Util;
import com.mambocosmo.urzasoracle.repositories.CardRepository;
import com.mambocosmo.urzasoracle.repositories.CommentOnCardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = false)
public class CardService extends GenericService<Card, CardDTO, CardConverter, CardRepository> {

    private final CardExpansionSetService EXPANSIONSETSERVICE;
    private final CardConverter CARDCONVERTER;
    private final CommentOnCardRepository COMMENTONCARDREPOSITORY;
    private final CardExpansionSetService SETSERVICE;
    @Value("${urza.bulkfetchtype}")
    private String bulkdatadownloadtype;

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

    public List<Card> getEntityByName(String name) {
        return getREPOSITORY().findByName(name);
    }

    public List<CardDTO> getByName(String name) {
        List<CardDTO> out = getREPOSITORY().findByNameContainingIgnoreCase(name).stream().map(e -> {
            return getCONVERTER().fromEToD(e);
        }).toList();
        return out;
    }

    // TODO IMPORTANT THIS METHOD IS HERE ONLY FOR COMMENT TESTING, CREATE COMMENT
    // DTO AND USE THAT
    public List<Card> getByNameEntity(String name) {
        List<Card> out = getREPOSITORY().findByNameContainingIgnoreCase(name).stream().map(e -> {
            return e;
        }).toList();
        return out;
    }

    public Card getByIdEntity(UUID id) {
        return getREPOSITORY().findById(id).orElse(null);
    }

    public Page<CardDTO> getByNamePaged(String name, Integer numeroPagina, Integer dimensione) {
        Pageable pageable = PageRequest.of(numeroPagina, dimensione, Sort.by(Sort.Direction.DESC, "released"));
        Page<Card> page = getREPOSITORY().findByNameContainingIgnoreCase(name, pageable);

        return page.map(card -> getCONVERTER().fromEToD(card));
    }

    // Prendi una singola carta per ID (UUID)
    public CardDTO getCardById(UUID id) {
        return getCONVERTER().fromEToD(getREPOSITORY().findById(id).orElse(null));
    }

    public List<Card> generateCardsFromJSON(String path) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {

            cardData = mapper.readTree(new File(path));
            List<Card> cardList = new ArrayList<>();

            Long myTimer = System.nanoTime();
            System.out.println("Starting save operation");
            cardData.forEach(e -> {
                try {
                    Card myCard = new Card();
                    myCard = mapper.treeToValue(e, new TypeReference<Card>() {
                    });
                    myCard.setExpansion(
                            getEXPANSIONSETSERVICE().getEntityByID(UUID.fromString(e.get("set_id").asText())));
                    cardList.add(myCard);
                } catch (JsonProcessingException | IllegalArgumentException e1) {
                    System.out.println("Error generating card!!! " + e1.getMessage());
                }
            });
            cardList.forEach(e -> {
                save(e);
                System.out.println("Saved card: " + e.getName());
            });

            System.out.println("Completed card generation - Generated " + cardList.size() + " cards in "
                    + Double.valueOf((System.nanoTime() - myTimer)) / 1000000000 + " seconds");

            return cardList;
        } catch (IOException e) {
            return null;
        }
    }

    // @EventListener(ApplicationReadyEvent.class)
    public String fetchBulkData() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bulkEntries;
        String filePath;
        String bPath = Util.downloadTempFile(
                "https://api.scryfall.com/bulk-data/" + bulkdatadownloadtype,
                "bulkdata.json");
        try {

            bulkEntries = mapper.readTree(new File(bPath));

            System.out.println(bulkEntries.get("type"));

            System.out.println("Fetching up to date " + bulkEntries.get("type").asText());
            filePath = Util.downloadTempFile(
                    bulkEntries.get("download_uri").asText(),
                    bulkdatadownloadtype + ".json");
            return filePath;

        } catch (IOException e) {
            return null;
        }
    }

    public Map<String, Integer> getUniqueCardFaces() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/json/oracles.json"));
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
        System.out.println("Requested page of " + dimensione + " cards");
        Pageable pageable = PageRequest.of(numeroPagina, dimensione, Sort.by(Sort.Direction.DESC, "released"));
        Page<Card> page = getREPOSITORY().findAll(pageable);

        return page.map(card -> getCONVERTER().fromEToD(card));
    }

    public Page<CardDTO> getRandomPaged(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "released"));
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
            // System.out.println("adding default artwork exclusion");
            q += " nonplayable:exclude";
            // System.out.println(q);
        }

        for (SearchCriteria query : SearchCriteria.StringToCriteria(q)) {
            QueryParser cardQ = new QueryParser(query);
            spec = spec.and(cardQ);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "released"));

        return getREPOSITORY().findAll(spec, pageable).map(e -> getCONVERTER().fromEToD(e));

    }

    @EventListener(ApplicationReadyEvent.class)
    public boolean populateDatabaseIfNone() {
        long dbelemnumber = getREPOSITORY().count();
        long dbsetnumber = getSETSERVICE().count();
        if (dbsetnumber == 0L) {
            System.out.println("Fetching up to date set data");
            Util.downloadTempFile("https://api.scryfall.com/sets/", "sets.json");
            getSETSERVICE().generateSetsFromJSON(Util.downloadTempFile("https://api.scryfall.com/sets/", "sets.json"));
        }
        System.out.println("Number of card entries in database: " + dbelemnumber);
        if (dbelemnumber == 0L) {
            System.out.println(
                    "Populating empty database with default card pool - you can change the desired pool from application.properties");
            if (bulkdatadownloadtype.trim().equalsIgnoreCase("selected_batch")) {
                generateCardsFromJSON("urzasoracle\\src\\main\\resources\\json\\selected_batch.json");
            } else {
                generateCardsFromJSON(fetchBulkData());
            }
        }
        return false;
    }

}