package com.mambocosmo.urzasoracle.controllers;

import java.util.HashSet;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.misc.Utils.Util;
import com.mambocosmo.urzasoracle.services.ArtistService;
import com.mambocosmo.urzasoracle.services.CardPartService;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;


@Data
@RestController
@RequestMapping("api")
public class CardRestController {

    private final CardService SERVICE;

    private final ArtistService ARTSERVICE;
    private final CardPartService CARDPARTSERVICE;

    @GetMapping("/saveAll")
    public String saveAll() {
        List<Card> myCards = Util.generateAllCards();
        System.out.println("Saving card with its relationships");
        Card card = myCards.get(4);
        myCards.forEach(e->{
        // Initialize sets if null to prevent NPE
        if (e.getArtistRef() == null) {
            e.setArtistRef(new HashSet<>());
        }
        if (e.getAll_parts() == null) {
            e.setAll_parts(new HashSet<>());
        }
        // Save the card - cascade will handle artists and parts
        getSERVICE().save(e);
        System.out.println("Saved card: " + e.getName());
    });
        return myCards.size() + " cards saved.";
    }

    @GetMapping("/byname")
    public String getByName(@RequestParam String name) {
        return getSERVICE().getByName(name).get(0).toString();
    }
    
}
