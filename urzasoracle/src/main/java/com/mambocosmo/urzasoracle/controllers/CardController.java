package com.mambocosmo.urzasoracle.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.Card;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;

@Controller
@Data
public class CardController {

    // private final CardRepository cardRepository;
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
        return "cards";
    }

    @GetMapping
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
}
