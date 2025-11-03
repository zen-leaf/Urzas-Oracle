package com.mambocosmo.urzasoracle.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.DTO.CommentOnCardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.CardService;
import com.mambocosmo.urzasoracle.services.CommentOnCardService;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;


@Data
@RestController
@RequestMapping("api/CommentController")
public class CommentOnCardController {

    private final CardService CARDSERVICE;
    private final UrzaUserService URZAUSERSERVICE;
    private final CardConverter CARDCONVERTER;
    private final CommentOnCardService COMMENTONCARDSERVICE;

    // api/CommentController/comment-test
    @GetMapping("/comment-test")
    public String commentTest() {
        CommentOnCard coc = new CommentOnCard();
        coc.setCard(getCARDSERVICE().getByNameEntity("Amarant Coral").get(0));
        coc.setUser(getURZAUSERSERVICE().findByUsername("a"));
        coc.setComment("Testo del Commento");

        return "saved:" + getCOMMENTONCARDSERVICE().save(coc) +
                "";
    }

    @PostMapping("/add-comment")
    @ResponseBody
    public ResponseEntity<CommentOnCardDTO> newComment(
            @RequestParam String commentText,
            @RequestParam UUID currentCardId,
            // @RequestParam(required = false, defaultValue = "standard") String mainDeckFormat,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        UrzaUser user = getURZAUSERSERVICE().findByUsername(username);

        try {
            CommentOnCardDTO coc = getCOMMENTONCARDSERVICE().createCommentOnCard(
                    user.getId(), currentCardId, commentText);
            return ResponseEntity.status(HttpStatus.CREATED).body(coc);
        } catch (Exception e) {
            System.err.println("Error saving comment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update-comment")
    @ResponseBody
    public ResponseEntity<CommentOnCardDTO> updateComment(
            @PathVariable UUID id,
            @RequestParam String newText,
            // @RequestParam(required = false) String description,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // String username = authentication.getName();
        // UrzaUser user = getURZAUSERSERVICE().findByUsername(username);

        CommentOnCardDTO updatedCoc = getCOMMENTONCARDSERVICE().updateCommentOnCard(
                id, newText);

        if (updatedCoc == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(updatedCoc);
    }

    @DeleteMapping("/delete-comment")
    @ResponseBody
    public ResponseEntity<Void> deleteComment(
            @PathVariable UUID id,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // String username = authentication.getName();
        // UrzaUser user = getURZAUSERSERVICE().findByUsername(username);

        boolean deleted = getCOMMENTONCARDSERVICE().deleteCommentOnCard(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.noContent().build();
    }

}
