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
import com.mambocosmo.urzasoracle.services.CardExpansionSetService;

import lombok.Data;

@Data
@RestController
@RequestMapping("api/cardexpansionset")
public class CardExspansionSetController {
    private final CardExpansionSetService CARDEXPANSIONSETSERVICE;

    @GetMapping("/allcardsExpansionSet")
    public ResponseEntity<List<CardExpansionSetDTO>> getAllCardExpansionSet() {
        List<CardExpansionSetDTO> cardExpansionSet = CARDEXPANSIONSETSERVICE.getAll();

        return ResponseEntity.ok().body(cardExpansionSet);
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<CardExpansionSetDTO> CardExpansionSetById(@PathVariable UUID id) {
        CardExpansionSetDTO c = CARDEXPANSIONSETSERVICE.getByID(id);
        if (c != null) {

            return ResponseEntity.ok().body(c);
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(CARDEXPANSIONSETSERVICE.save(params));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCardExpansionSet(@PathVariable UUID id) {
        CARDEXPANSIONSETSERVICE.delete(id);

        return ResponseEntity.noContent().build();
    }

}
