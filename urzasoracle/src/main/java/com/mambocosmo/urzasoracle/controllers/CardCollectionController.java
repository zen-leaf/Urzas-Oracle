package com.mambocosmo.urzasoracle.controllers;

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

import lombok.Data;

@Data
@RestController
@RequestMapping("api/CardCollectionController")
public class CardCollectionController {
   private final CardCollectionService CARDCOLLECTIONSERVICE;

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

}
