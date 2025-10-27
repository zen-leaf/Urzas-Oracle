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

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.DTO.CardPartDTO;
import com.mambocosmo.urzasoracle.services.CardPartService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/CardPart")
public class CardPartController {

    private final CardPartService CARDPARTSERVICE;

    @GetMapping("/allCardPart")
    public ResponseEntity<List<CardPartDTO>> getAllCardExpansionSet() {
        List<CardPartDTO> cardParts = CARDPARTSERVICE.getAll();

        return ResponseEntity.ok().body(cardParts);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<CardPartDTO> CardPartById(@PathVariable UUID id) {
        CardPartDTO c = CARDPARTSERVICE.getByID(id);
        if (c != null) {

            return ResponseEntity.ok().body(c);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(CARDPARTSERVICE.save(params));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCardPart(@PathVariable UUID id) {
        CARDPARTSERVICE.delete(id);

        return ResponseEntity.noContent().build();
    }

}
