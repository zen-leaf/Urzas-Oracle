package com.mambocosmo.urzasoracle.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.services.CardService;

import lombok.Data;

@Controller
@Data
public class CardController {

    private final CardService CARDSERVICE;

    @GetMapping("/cards")
    public String getAllCards(Model model, @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Page<CardDTO> cardPage = getCARDSERVICE().getAllPaged(page, size);
        Page<CardDTO> cardPage = getCARDSERVICE().searchByQueryPaged(q, page, size);
        model.addAttribute("lista", cardPage.getContent());
        model.addAttribute("currentPage", cardPage.getNumber());
        model.addAttribute("totalPages", cardPage.getTotalPages());
        model.addAttribute("totElements", cardPage.getTotalElements());
        model.addAttribute("pageSize", cardPage.getSize());
        model.addAttribute("lastQuery", q);
        model.addAttribute("active", "cards");
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

            List<CommentOnCard> comments = getCARDSERVICE().getCommentsByCard(card.getId()); 
            // Log per debug
            System.out.println("Card found: " + card.getName());

            model.addAttribute("card", card);
            model.addAttribute("active", "cards");
            model.addAttribute("comments", comments);
            return "cardinfo";
        } catch (Exception e) {
            System.err.println("Error loading card: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/cards";
        }
    }
}
