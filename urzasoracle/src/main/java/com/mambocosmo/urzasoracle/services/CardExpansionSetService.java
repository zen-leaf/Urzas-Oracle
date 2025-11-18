package com.mambocosmo.urzasoracle.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public long count() {
        return getREPOSITORY().count();
    }

    public List<CardExpansionSet> generateSetsFromJSON(File file) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode setData = mapper.readTree(file).get("data");
            // System.out.println(setData.get("data"));

            List<CardExpansionSet> setList = new ArrayList<>();
            Long myTimer = System.nanoTime();
            System.out.println("Starting set generation");
            setData.forEach(e -> {
                try {
                    CardExpansionSet mySet = null;
                    mySet = mapper.treeToValue(e, new TypeReference<CardExpansionSet>() {
                    });
                    // System.out.println(mySet.getName());
                    setList.add(mySet);
                } catch (JsonProcessingException | IllegalArgumentException e1) {
                    System.out.println("Error generating set!!! " + e1.getMessage());
                    e1.printStackTrace();
                }
            });

            setList.forEach(e -> {
                save(e);
                System.out.println("Saved set: " + e.getName() + "(" + e.getCode() + ")");
            });
            System.out.println("Completed set generation - Generated " + setList.size() +
                    " sets in "
                    + Double.valueOf((System.nanoTime() - myTimer)) / 1000000000 + " seconds");
            System.out.println("DOWNLOADSETS: " + file.getPath());
            return setList;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // TODO Auto-generated catch block
            return null;

        }
    }

    public List<CardExpansionSet> generateSetsFromJSON(String path) {
        File file = new File(path);
        return generateSetsFromJSON(file);

    }

}
