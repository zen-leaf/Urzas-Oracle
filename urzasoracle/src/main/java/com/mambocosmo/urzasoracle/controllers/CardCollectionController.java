package com.mambocosmo.urzasoracle.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.mambocosmo.urzasoracle.services.CardCollectionService;
import com.mambocosmo.urzasoracle.services.UserService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/CardCollectionController")
public class CardCollectionController {
   private final CardCollectionService CARDCOLLECTIONSERVICE;
   private final UserService USERSERVICE;

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
   
   // CREATE DUMMY USER -> CREATE DUMMY DECK -> MODIFY DUMMY DECK -> TEST USER-DECK CASCADE DELETION
   // api/CardCollectionController/create-deckTEST
   @GetMapping("/create-deckTEST")
   public String createDeckTest() {
      // System.out.println("test print" + getUSERSERVICE().getByUsername("dummy_user2").getId().toString());
      Map<String,String> testMap = new HashMap<>();
      testMap.put("name", "deck_dummyello?");
      testMap.put("description", "the worst deck ever");


      return "saved:" + getCARDCOLLECTIONSERVICE().save(testMap) +
         "\nquello " + getUSERSERVICE().getByUsername("dummy_user2").getId().toString();
      // return "redirect:/allCardCollection";
   }
   
   // api/CardCollectionController/create-userTEST
   @GetMapping("/create-userTEST")
   public String createUserTest() {
      Map<String,String> testMap = new HashMap<>();
      testMap.put("username", "dummy_user2");
      testMap.put("password", "clear");
      testMap.put("email", "asd@asd.it");

      return "saved:" + getUSERSERVICE().save(testMap) +
         "";
      // return "redirect:/allCardCollection";
   }

}
