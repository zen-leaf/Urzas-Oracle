package com.mambocosmo.urzasoracle.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.DTO.CardInDeckDTO;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.CardCollectionService;
import com.mambocosmo.urzasoracle.services.CardService;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Controller
@RequestMapping("/decks")
@Data
public class DeckController {

    private final CardCollectionService cardCollectionService;
    private final UrzaUserService urzaUserService;
    private final CardService cardService;

    @GetMapping
    public String listDecks(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);
        List<CardCollectionDTO> userDecks = cardCollectionService.getDecksByUser(user.getId());

        model.addAttribute("decks", userDecks);
        model.addAttribute("active", "decks");
        return "decks";
    }

    @GetMapping("/{id}")
    public String deckDetail(@PathVariable UUID id, Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardCollectionDTO deck = cardCollectionService.getByID(id);

        if (deck == null) {
            return "redirect:/decks";
        }

        List<CardInDeckDTO> cards = cardCollectionService.getCardsByDeck(id);
        int totalCards = cards.stream().mapToInt(c -> Integer.parseInt(c.getQuantity())).sum();

        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCards", totalCards);
        model.addAttribute("active", "decks");

        return "deck-detail";
    }

    @GetMapping("/api/cards")
    @ResponseBody
    public List<CardDTO> getAllCardsAPI() {
        return cardService.getAllPaged(0, Integer.MAX_VALUE).getContent();
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<CardCollectionDTO> createDeck(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false, defaultValue = "standard") String mainDeckFormat,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        try {
            CardCollectionDTO newDeck = cardCollectionService.createDeck(user.getId(), name, description, mainDeckFormat);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
        } catch (Exception e) {
            System.err.println("❌ ERRORE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CardCollectionDTO> updateDeck(
            @PathVariable UUID id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardCollectionDTO updatedDeck = cardCollectionService.updateDeck(id, user.getId(), name, description);
        if (updatedDeck == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteDeck(
            @PathVariable UUID id,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        boolean deleted = cardCollectionService.deleteDeck(id, user.getId());
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{deckId}/cards/{cardId}")
    @ResponseBody
    public ResponseEntity<CardInDeckDTO> addCardToDeck(
            @PathVariable UUID deckId,
            @PathVariable UUID cardId,
            @RequestParam(defaultValue = "1") String quantity,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardInDeckDTO result = cardCollectionService.addCardToDeck(deckId, user.getId(), cardId, quantity);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{deckId}/cards/{cardId}")
    @ResponseBody
    public ResponseEntity<Void> removeCardFromDeck(
            @PathVariable UUID deckId,
            @PathVariable UUID cardId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        boolean removed = cardCollectionService.removeCardFromDeck(deckId, user.getId(), cardId);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{deckId}/cards/{cardId}/increase")
    @ResponseBody
    public ResponseEntity<CardInDeckDTO> increaseCardQuantity(
            @PathVariable UUID deckId,
            @PathVariable UUID cardId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardInDeckDTO result = cardCollectionService.increaseCardQuantity(deckId, user.getId(), cardId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{deckId}/cards/{cardId}/decrease")
    @ResponseBody
    public ResponseEntity<CardInDeckDTO> decreaseCardQuantity(
            @PathVariable UUID deckId,
            @PathVariable UUID cardId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardInDeckDTO result = cardCollectionService.decreaseCardQuantity(deckId, user.getId(), cardId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(result);
    }
}
