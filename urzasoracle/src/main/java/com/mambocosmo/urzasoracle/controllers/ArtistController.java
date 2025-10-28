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

import com.mambocosmo.urzasoracle.DTO.ArtistDTO;
import com.mambocosmo.urzasoracle.services.ArtistService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/artist")
public class ArtistController {

   private final ArtistService ARTISTSERVICE;

   @GetMapping("/allArtist")
   public ResponseEntity<List<ArtistDTO>> getAllArtists() {
      List<ArtistDTO> artists = ARTISTSERVICE.getAll();

      return ResponseEntity.ok().body(artists);
   }

   @GetMapping("byId/{id}")
   public ResponseEntity<ArtistDTO> CardExpansionSetById(@PathVariable UUID id) {
      ArtistDTO a = ARTISTSERVICE.getByID(id);
      if (a != null) {

         return ResponseEntity.ok().body(a);
      } else {

         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }
   }

   @PostMapping("/save")
   public ResponseEntity<Boolean> save(@RequestParam Map<String, String> params) {
      return ResponseEntity.ok().body(ARTISTSERVICE.save(params));
   }

   @PostMapping("/delete/{id}")
   public ResponseEntity<Void> deleteCardExpansionSet(@PathVariable UUID id) {
      ARTISTSERVICE.delete(id);

      return ResponseEntity.noContent().build();
   }

   @GetMapping("/byname")
   public String getByName(@RequestParam String name) {
      return getARTISTSERVICE().getByName(name).toString();
   }

}
