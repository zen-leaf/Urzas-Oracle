package com.mambocosmo.urzasoracle.controllers;

import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.repositories.CardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public String listCards(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "12") int size,
            Model model) {

        List<Card> allCards;
        if (q != null && !q.isBlank()) {
            allCards = cardRepository.findByNameContainingIgnoreCase(q);
        } else {
            allCards = cardRepository.findAll();
        }

        int total = allCards.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);

        List<Card> pageContent;
        if (fromIndex >= total || fromIndex < 0) {
            pageContent = List.of(); 
        } else {
            pageContent = allCards.subList(fromIndex, toIndex);
        }

        
        Page<Card> cardPage = new PageImpl<>(pageContent, PageRequest.of(page, size), total);

      
        model.addAttribute("cards", cardPage);
        model.addAttribute("query", q != null ? q : "");

        return "cards"; 
    }
}
