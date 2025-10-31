package com.mambocosmo.urzasoracle.misc.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
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

}
