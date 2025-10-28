package com.mambocosmo.urzasoracle.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.misc.Utils.Util;
import com.mambocosmo.urzasoracle.services.ArtistService;
import com.mambocosmo.urzasoracle.services.CardExpansionSetService;
import com.mambocosmo.urzasoracle.services.CardPartService;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api")
public class CardRestController {

    private final CardService CARDSERVICE;
    private final CardExpansionSetService EXPANSIONSETSERVICE;

    private final ArtistService ARTSERVICE;
    private final CardPartService CARDPARTSERVICE;

    @GetMapping("/saveAll")
    public String saveAll() {
        List<Card> myCards = getCARDSERVICE().generateAllCardsFromJSON();
        System.out.println("Saving card 'n' sets with its relationships");
        myCards.forEach(e -> {
            getCARDSERVICE().save(e);
            System.out.println(e.getCard_faces());
            System.out.println("Saved card: " + e.getName());
        });
        // Map<String,Integer> uniqueFaces = getCARDSERVICE().getUniqueCardFaces();
        // return uniqueFaces==null?"No unique faces found.":uniqueFaces.size()+" unique
        // faces found and "+myCards.size()+" cards saved.";
        return myCards.size() + " cards saved.";
    }

    @GetMapping("/delete")
    public String deleteone(@RequestParam String param) {
        return new String();
    }

    @GetMapping("/saveSet")
    public String saveSet() {
        List<CardExpansionSet> mySets = Util.generateAllSets();
        System.out.println("Saving card 'n' sets with its relationships");
        // Card card = myCards.get(4);
        // CardExpansionSet set = mySets.get(4);
        mySets.forEach(e -> {
            // Save the card - cascade will handle artists and parts
            EXPANSIONSETSERVICE.save(e);
            System.out.println("Saved set: " + e.getName());
        });

        return mySets.size() + " sets saved.";
    }

    // @GetMapping("/byname")
    // public ResponseEntity<List<CardDTO>> getByName(@RequestParam String name) {
    // List<CardDTO> cards = getCARDSERVICE().getByName(name);
    // if (cards.size() == 0) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }

    // return ResponseEntity.ok(getCARDSERVICE().getByName(name));

    // }

    @GetMapping("/byname")
    public ResponseEntity<Page<CardDTO>> getByName(@RequestParam String name,
            @RequestParam(defaultValue = "0") Integer size,
            @RequestParam(defaultValue = "10") Integer page) {
        return ResponseEntity.ok(CARDSERVICE.getByNamePaged(name, page, size));
    }

    @GetMapping("/setbyname")
    public String getMethodName(@RequestParam String name) {
        return getEXPANSIONSETSERVICE().getByName(name).toString();
    }

    @GetMapping("/allCard")
    public ResponseEntity<Page<CardDTO>> getAllCard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CardDTO> cards = CARDSERVICE.getAllPaged(page, size);
        return ResponseEntity.ok(cards);
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
