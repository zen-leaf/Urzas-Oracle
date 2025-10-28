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

import com.mambocosmo.urzasoracle.DTO.StructuredCardCollectionDTO;
import com.mambocosmo.urzasoracle.services.StructuredCardCollectionService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/StructuredCardCollectionController")
public class StructuredCardCollectionController {
   private final StructuredCardCollectionService STRUCTUREDCARDCOLLECTIONSERVICE;

   @GetMapping("/allStructuredCardCollection")
   public ResponseEntity<List<StructuredCardCollectionDTO>> getAllCardCollection() {
      List<StructuredCardCollectionDTO> structuredCardCollection = STRUCTUREDCARDCOLLECTIONSERVICE.getAll();

      return ResponseEntity.ok().body(structuredCardCollection);
   }

   @GetMapping("byId/{id}")
   public ResponseEntity<StructuredCardCollectionDTO> CardExpansionSetById(@PathVariable UUID id) {
      StructuredCardCollectionDTO s = (StructuredCardCollectionDTO) STRUCTUREDCARDCOLLECTIONSERVICE.getByID(id);
      if (s != null) {

         return ResponseEntity.ok().body(s);
      } else {

         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
   }

   @PostMapping("/save")
   public ResponseEntity<Boolean> save(@RequestParam Map<String, String> params) {
      return ResponseEntity.ok().body(STRUCTUREDCARDCOLLECTIONSERVICE.save(params));
   }

   @PostMapping("/delete/{id}")
   public ResponseEntity<Void> deleteCardExpansionSet(@PathVariable UUID id) {
      STRUCTUREDCARDCOLLECTIONSERVICE.delete(id);

      return ResponseEntity.noContent().build();
   }

   @GetMapping("/byname")
   public String getByName(@RequestParam String name) {
      return getSTRUCTUREDCARDCOLLECTIONSERVICE().getByName(name).toString();
   }

}
