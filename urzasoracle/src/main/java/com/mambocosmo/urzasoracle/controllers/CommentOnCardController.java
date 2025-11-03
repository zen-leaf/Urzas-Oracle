package com.mambocosmo.urzasoracle.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.CardCollectionService;
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
        coc.setComment("DIO SANTOOOOOOO");

        return "saved:" + getCOMMENTONCARDSERVICE().save(coc) +
                "";
    }

}
