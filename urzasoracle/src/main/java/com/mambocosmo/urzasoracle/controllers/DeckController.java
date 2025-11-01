package com.mambocosmo.urzasoracle.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // ============ DECKS PUBBLICI ============
    @GetMapping
    public String listAllDecks(Model model, Authentication authentication) {
        List<CardCollectionDTO> allDecks = cardCollectionService.getAllDecks();

        for (CardCollectionDTO deck : allDecks) {
            Integer cardCount = cardCollectionService.getTotalCardsByDeckId(deck.getId());
            deck.setTotalCards(cardCount != null ? cardCount : 0);

            List<CardInDeckDTO> allCards = cardCollectionService.getCardsByDeck(deck.getId());
            List<CardInDeckDTO> preview = allCards.stream()
                    .limit(4)
                    .collect(Collectors.toList());
            deck.setPreviewCards(preview);
        }

        model.addAttribute("decks", allDecks);
        model.addAttribute("isLoggedIn", authentication != null && authentication.isAuthenticated());
        model.addAttribute("active", "decks");
        return "decks";
    }

    @GetMapping("/public/{id}")
    public String viewPublicDeck(@PathVariable UUID id, Model model, Authentication authentication) {
        CardCollectionDTO deck = cardCollectionService.getByID(id);

        if (deck == null) {
            return "redirect:/decks";
        }

        boolean isOwner = false;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            isOwner = deck.getOwner() != null && deck.getOwner().equals(username);
        }

        List<CardInDeckDTO> cards = cardCollectionService.getCardsByDeck(id);
        int totalCards = cards.stream()
                .mapToInt(c -> Integer.parseInt(c.getQuantity()))
                .sum();

        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCards", totalCards);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("isLoggedIn", authentication != null && authentication.isAuthenticated());
        model.addAttribute("active", "decks");

        return "public-deck-detail";
    }

    @PostMapping("/public/{deckId}/clone")
    @ResponseBody
    public ResponseEntity<CardCollectionDTO> cloneDeck(
            @PathVariable UUID deckId,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);

        CardCollectionDTO clonedDeck = cardCollectionService.cloneDeck(deckId, user.getId());
        if (clonedDeck == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(clonedDeck);
    }

    // ============ I MIEI MAZZI ============
    @GetMapping("/mydecks")
    public String listMyDecks(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        String username = authentication.getName();
        UrzaUser user = urzaUserService.findByUsername(username);
        List<CardCollectionDTO> userDecks = cardCollectionService.getDecksByUser(user.getId());

        for (CardCollectionDTO deck : userDecks) {
            Integer cardCount = cardCollectionService.getTotalCardsByDeckId(deck.getId());
            deck.setTotalCards(cardCount != null ? cardCount : 0);

            List<CardInDeckDTO> allCards = cardCollectionService.getCardsByDeck(deck.getId());
            List<CardInDeckDTO> preview = allCards.stream()
                    .limit(4)
                    .collect(Collectors.toList());
            deck.setPreviewCards(preview);
        }

        model.addAttribute("decks", userDecks);
        model.addAttribute("active", "mydecks");
        return "mydecks";
    }

    @GetMapping("/mydecks/{id}")
    public String deckDetail(@PathVariable UUID id, Model model, Authentication authentication) {
        CardCollectionDTO deck = cardCollectionService.getByID(id);

        if (deck == null) {
            return "redirect:/decks/mydecks";
        }

        boolean isOwner = false;
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            isOwner = deck.getOwner() != null && deck.getOwner().equals(username);
        }

        if (!isOwner) {
            return "redirect:/decks/mydecks";
        }

        List<CardInDeckDTO> cards = cardCollectionService.getCardsByDeck(id);
        int totalCards = cards.stream()
                .mapToInt(c -> Integer.parseInt(c.getQuantity()))
                .sum();

        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCards", totalCards);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("active", "mydecks");

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
            CardCollectionDTO newDeck = cardCollectionService.createDeck(
                user.getId(), name, description, mainDeckFormat
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(newDeck);
        } catch (Exception e) {
            System.err.println("❌ ERRORE: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/mydecks/{id}")
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

        CardCollectionDTO updatedDeck = cardCollectionService.updateDeck(
            id, user.getId(), name, description
        );

        if (updatedDeck == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(updatedDeck);
    }

    @DeleteMapping("/mydecks/{id}")
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

    @PostMapping("/mydecks/{deckId}/cards/{cardId}")
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

        CardInDeckDTO result = cardCollectionService.addCardToDeck(
            deckId, user.getId(), cardId, quantity
        );

        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/mydecks/{deckId}/cards/{cardId}")
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

        boolean removed = cardCollectionService.removeCardFromDeck(
            deckId, user.getId(), cardId
        );

        if (!removed) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/mydecks/{deckId}/cards/{cardId}/increase")
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

        CardInDeckDTO result = cardCollectionService.increaseCardQuantity(
            deckId, user.getId(), cardId
        );

        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/mydecks/{deckId}/cards/{cardId}/decrease")
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

        CardInDeckDTO result = cardCollectionService.decreaseCardQuantity(
            deckId, user.getId(), cardId
        );

        if (result == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(result);
    }

    private Map<String, List<CardInDeckDTO>> categorizeCards(List<CardInDeckDTO> cards) {
        Map<String, List<CardInDeckDTO>> result = new HashMap<>();

        result.put("Commander", new ArrayList<>());
        result.put("Planeswalker", new ArrayList<>());
        result.put("Creature", new ArrayList<>());
        result.put("Instant", new ArrayList<>());
        result.put("Sorcery", new ArrayList<>());
        result.put("Artifact", new ArrayList<>());
        result.put("Enchantment", new ArrayList<>());
        result.put("Land", new ArrayList<>());

        for (CardInDeckDTO card : cards) {
            String typeline = card.getTypeline() != null ? card.getTypeline() : "";

            if (typeline.contains("Legendary") && typeline.contains("Creature")) {
                result.get("Commander").add(card);
            } else if (typeline.contains("Planeswalker")) {
                result.get("Planeswalker").add(card);
            } else if (typeline.contains("Creature")) {
                result.get("Creature").add(card);
            } else if (typeline.contains("Instant")) {
                result.get("Instant").add(card);
            } else if (typeline.contains("Sorcery")) {
                result.get("Sorcery").add(card);
            } else if (typeline.contains("Artifact")) {
                result.get("Artifact").add(card);
            } else if (typeline.contains("Enchantment")) {
                result.get("Enchantment").add(card);
            } else if (typeline.contains("Land")) {
                result.get("Land").add(card);
            }
        }

        for (List<CardInDeckDTO> category : result.values()) {
            category.sort(Comparator.comparing(CardInDeckDTO::getCardName));
        }

        return result;
    }
}
