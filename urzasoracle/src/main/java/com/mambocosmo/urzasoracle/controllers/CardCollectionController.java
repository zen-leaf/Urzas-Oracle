package com.mambocosmo.urzasoracle.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardInDeck;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.CardCollectionService;
import com.mambocosmo.urzasoracle.services.CardService;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/CardCollectionController")
public class CardCollectionController {
   private final CardCollectionService CARDCOLLECTIONSERVICE;
   private final UrzaUserService URZAUSERSERVICE;
   private final CardService CARDSERVICE;
   private final CardConverter CARDCONVERTER;

   @GetMapping("/allCardCollection")
   public ResponseEntity<List<CardCollectionDTO>> getAllCardCollection() {
      List<CardCollectionDTO> cardCollections = CARDCOLLECTIONSERVICE.getAll();

      return ResponseEntity.ok().body(cardCollections);
   }

   @GetMapping("byId/{id}")
   public ResponseEntity<CardCollectionDTO> CardExpansionSetById(@PathVariable UUID id) {
      CardCollectionDTO c = CARDCOLLECTIONSERVICE.getByID(id);
      if (c != null) {

         return ResponseEntity.ok().body(c);
      } else {

         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
   }

   @PostMapping("/save")
   public ResponseEntity<Boolean> save(@RequestParam Map<String, String> params) {
      return ResponseEntity.ok().body(CARDCOLLECTIONSERVICE.save(params));
   }

   @PostMapping("/delete/{id}")
   public ResponseEntity<Void> deleteCardExpansionSet(@PathVariable UUID id) {
      CARDCOLLECTIONSERVICE.delete(id);

      return ResponseEntity.noContent().build();
   }

   @GetMapping("/byname")
   public String getByName(@RequestParam String name) {
      return getCARDCOLLECTIONSERVICE().getByName(name).toString();
   }

   // api/CardCollectionController/crea-deckPROVA
   @PostMapping("/create-deck")
   public String createCardExpansionSet(@RequestParam Map<String, String> map) {
      getCARDCOLLECTIONSERVICE().save(map);
      return "redirect:/allCardCollection";
   }

   // TODO following methods for testing only
   // CREATE DUMMY USER -> CREATE DUMMY DECK -> MODIFY DUMMY DECK -> TEST USER-DECK
   // CASCADE DELETION
   // api/CardCollectionController/create-deckTEST
   @GetMapping("/create-deckTEST")
   public String createDeckTest() {
      // System.out.println("test print" +
      // getUSERSERVICE().getByUsername("dummy_user2").getId().toString());
      Map<String, String> testMap = new HashMap<>();
      testMap.put("name", "deck_dummy");
      testMap.put("description", "the worst deck ever");
      testMap.put("mainDeckFormat", "free");

      return "saved:" + getCARDCOLLECTIONSERVICE().save(testMap) +
            "\nquello " + getURZAUSERSERVICE().findByUsername("dummy_user").getId().toString();
      // return "redirect:/allCardCollection";
   }

   // api/CardCollectionController/create-userTEST
   @GetMapping("/create-userTEST")
   public String createUserTest() {
      Map<String, String> testMap = new HashMap<>();
      testMap.put("username", "dummy_user");
      testMap.put("password", "clear");
      testMap.put("email", "asd@asd.it");

      return "saved:" + getURZAUSERSERVICE().save(testMap) +
            "";
      // return "redirect:/allCardCollection";
   }

   @GetMapping("/modify-userTEST")
   public String modifyUserTest() {
      Map<String, String> testMap = new HashMap<>();
      testMap.put("username", "dummy_user_modify");
      testMap.put("password", "clear2");
      testMap.put("email", "asd2@asd2.it");

      UrzaUser oldU = getURZAUSERSERVICE().findByUsername("dummy_user");

      oldU.setUsername(testMap.get("username"));
      oldU.setPassword(testMap.get("password"));
      oldU.setUsername(testMap.get("email"));

      return "saved:" + getURZAUSERSERVICE().save(oldU) +
            "";
      // return "redirect:/allCardCollection";
   }

   @GetMapping("/fill-testDECK")
   public String fillTestDeck() {
      CardCollection cc = getCARDCOLLECTIONSERVICE().getByName("deck_dummy").get(0);
      List<Card> allCards = getCARDSERVICE().getAllCardEntities();
      long seed = System.nanoTime();
      Collections.shuffle(allCards, new Random(seed));
      // System.out.println("printing cc : " + cc);
      cc.getCardList().clear();
      for (int i = 0; i < 50; i++) {
         Card card = allCards.get(i);
         // System.out.println("\ninderisco carta : " + card.getName());
         // CardInDeck cid = new CardInDeck(cc, card,
         // String.valueOf((Math.round(Math.random()*2))+1));
         // System.out.println("\ndentro card in deck : " + cid.toString());
         // cc.getCardList().add(cid);
         cc.getCardList().add(new CardInDeck(cc, card, String.valueOf((Math.round(Math.random() * 2)) + 1)));
         // System.out.println("\ncard list post add: " + cc.getCardList());

      }

      // System.out.println(" LAST PRINT !!!!!!!!!!!!!!!!!!!!!!!!!!!" +
      // cc.getCardList());

      getCARDCOLLECTIONSERVICE().save(cc);

      return "filled :" + cc;

   }

   // api/CardCollectionController/add-test-user-deck
   @GetMapping("/add-test-user-deck")
   public String addTestUserDeck() {
      try{
         // UrzaUser userCheck = getURZAUSERSERVICE().findByUsername("dummy_user");
         if (getURZAUSERSERVICE().findByUsername("dummy_user") == null){
            Map<String, String> testMap = new HashMap<>();
            testMap.put("username", "dummy_user");
            testMap.put("displayName", "dummy_user_display");
            testMap.put("password", "cane");
            testMap.put("email", "asd@asd.it");
            getURZAUSERSERVICE().registerUser(testMap);
         }

         // CardCollection deckCheck = getCARDCOLLECTIONSERVICE().getByName("deck_dummy").get(0);
         if (getCARDCOLLECTIONSERVICE().getByName("deck_dummy").size() == 0){
            createDeckTest();
         }

         fillTestDeck();

      }
      catch (Exception e){
         e.printStackTrace();
         return "error during generation";
      }
      return "created user and deck";
   }

}
