package com.mambocosmo.urzasoracle.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.misc.Utils.Util;
import com.mambocosmo.urzasoracle.services.ArtistService;
import com.mambocosmo.urzasoracle.services.CardExpansionSetService;
import com.mambocosmo.urzasoracle.services.CardPartService;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@RestController
@RequestMapping("api")
public class CardRestController {

    private final CardService CARDSERVICE;
    private final CardExpansionSetService SETSERVICE;

    private final ArtistService ARTSERVICE;
    private final CardPartService CARDPARTSERVICE;

    @GetMapping("/saveAll")
    public String saveAll() {
        List<Card> myCards = Util.generateAllCards();
        List<CardExpansionSet> mySets = Util.generateAllSets();
        System.out.println("Saving card 'n' sets with its relationships");
        // Card card = myCards.get(4);
        // CardExpansionSet set = mySets.get(4);
        mySets.forEach(e -> {
            // Save the card - cascade will handle artists and parts
            SETSERVICE.save(e);
            System.out.println("Saved set: " + e.getName());
        });
        myCards.forEach(e -> {
            // Initialize sets if null to prevent NPE
            if (e.getArtistRef() == null) {
                e.setArtistRef(new HashSet<>());
            }
            if (e.getAll_parts() == null) {
                e.setAll_parts(new HashSet<>());
            }
            // Save the card - cascade will handle artists and parts
            getCARDSERVICE().save(e);
            System.out.println("Saved card: " + e.getName());
        });

        return myCards.size() + " cards saved. \n" + mySets.size() + " sets saved.";
    }

    @GetMapping("/saveSet")
    public String saveSet() {
        List<CardExpansionSet> mySets = Util.generateAllSets();
        System.out.println("Saving card 'n' sets with its relationships");
        // Card card = myCards.get(4);
        // CardExpansionSet set = mySets.get(4);
        mySets.forEach(e -> {
            // Save the card - cascade will handle artists and parts
            SETSERVICE.save(e);
            System.out.println("Saved set: " + e.getName());
        });

        return mySets.size() + " sets saved.";
    }

    @GetMapping("/byname")
    public String getByName(@RequestParam String name) {
        return getCARDSERVICE().getByName(name).get(0).toString();
    }

    @GetMapping("/allCard")
    public ResponseEntity<List<CardDTO>> getAllCard() {
        List<CardDTO> cards = CARDSERVICE.getAll();

        return ResponseEntity.ok().body(cards);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<CardDTO> CardExpansionSetById(@PathVariable UUID id) {
        CardDTO c = (CardDTO) CARDSERVICE.getByID(id);
        if (c != null) {

            return ResponseEntity.ok().body(c);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCardExpansionSet(@PathVariable UUID id) {
        CARDSERVICE.delete(id);

        return ResponseEntity.noContent().build();
    }

}
