package com.mambocosmo.urzasoracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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