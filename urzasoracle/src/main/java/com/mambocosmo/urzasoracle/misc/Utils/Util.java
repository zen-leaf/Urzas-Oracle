package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambocosmo.urzasoracle.entities.Card;

public class Util {
    public static Set<String> generateUniqueFieldNames(String insideOf) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData = null;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/uniques.json"));
            Set<String> params = new HashSet<>();
            String searchfor = insideOf;
            cardData.forEach(e -> {
                if (e.get(searchfor) != null) {
                    e.get(searchfor).forEach(e1 -> e1.properties().forEach(e3 -> {
                        System.out.println(e3.getKey());
                        params.add(e3.getKey());
                    }));
                    // .forEach(e2 -> System.out.println(e2))
                    // System.out.println(e.get(searchfor).asText());
                    // params.add(e.get(searchfor).asText());
                }
            });
            return params;
            // cardData.get(0).properties().forEach(e -> System.out.println(e.getKey()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static List<Card> generateAllCards() {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode cardData;
        try {
            cardData = mapper.readTree(new File("urzasoracle/src/main/resources/uniques.json"));

            List<Card> cardList = new ArrayList<>();
            Long myTimer = System.nanoTime();
            System.out.println("start");
            cardData.forEach(e -> {
                try {
                    Card myCard = null;
                    myCard = mapper.treeToValue(e, new TypeReference<Card>() {
                    });
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
