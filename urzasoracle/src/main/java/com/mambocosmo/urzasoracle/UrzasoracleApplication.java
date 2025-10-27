package com.mambocosmo.urzasoracle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.mambocosmo.urzasoracle.services.CardService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.misc.Utils.Util;

@SpringBootApplication
public class UrzasoracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrzasoracleApplication.class, args);

		// List<Card> o = generateAllCards();
		//System.out.println(Util.generateUniqueFieldNames("all_parts"));
		// ObjectMapper mapper = new ObjectMapper();
		// JsonNode cardData = null;
		// Set<String> params = new HashSet<>();

		// try {
		// cardData = mapper.readTree(new
		// File("urzasoracle/src/main/resources/uniques.json"));
		// cardData.forEach(k -> k.properties().forEach(p -> params.add(p.getKey())));
		// } catch (Exception e) {

		// }
		// System.out.println(params);
		// }

	}

}