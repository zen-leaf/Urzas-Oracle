package com.mambocosmo.urzasoracle.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;
import org.springframework.data.domain.Page;

@Controller
@Data
public class CardController {

    private final CardService CARDSERVICE;

    @GetMapping("/cards")
    public String getAllCards(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CardDTO> cardPage = getCARDSERVICE().getAllPaged(page, size);
        model.addAttribute("lista", cardPage.getContent());
        model.addAttribute("currentPage", cardPage.getNumber());
        model.addAttribute("totalPages", cardPage.getTotalPages());
        model.addAttribute("totElements", cardPage.getTotalElements());
        model.addAttribute("pageSize", cardPage.getSize());
        model.addAttribute("active", "cards");
        return "cards";
    }

    @GetMapping("/cards/search")
    public String listCards(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {

        List<Card> allCards;
        // // if (q != null && !q.isBlank()) {
        // // allCards = cardRepository.findByNameContainingIgnoreCase(q);
        // // } else {
        // // allCards = cardRepository.findAll();
        // // }

        // int total = allCards.size();
        // int fromIndex = page * size;
        // int toIndex = Math.min(fromIndex + size, total);

        // List<Card> pageContent;
        // if (fromIndex >= total || fromIndex < 0) {
        // pageContent = List.of();
        // } else {
        // pageContent = allCards.subList(fromIndex, toIndex);
        // }

        // Page<Card> cardPage = new PageImpl<>(pageContent, PageRequest.of(page, size),
        // total);

        // model.addAttribute("cards", cardPage);
        // model.addAttribute("query", q != null ? q : "");

        return "cards";
    }

    @GetMapping("/cards/{id}")
    @Transactional(readOnly = true)
    public String getCardInfo(@PathVariable UUID id, Model model) {
        try {
            CardDTO card = getCARDSERVICE().getCardById(id);
            if (card == null) {
                System.out.println("Card not found with id: " + id);
                return "redirect:/cards";
            }

            // Log per debug
            System.out.println("Card found: " + card.getName());

            model.addAttribute("card", card);
            model.addAttribute("active", "cards");
            return "cardinfo";
        } catch (Exception e) {
            System.err.println("Error loading card: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/cards";
        }

    }
}
