package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CommentOnCardDTO;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.services.CardService;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Service
@Data
public class CommentOnCardConverter implements GenericConverter<CommentOnCard, CommentOnCardDTO>{

    private final CardService CARDSERVICE;
    private final UrzaUserService URZAUSERSERVICE;
    private final CardConverter CARDCONVERTER;

    @Override
    public CommentOnCard fromDToE(CommentOnCardDTO dto) {
        CommentOnCard e = new CommentOnCard();
        e.setCard(getCARDSERVICE().getByIdEntity(dto.getCardId()));
        e.setUser(getURZAUSERSERVICE().findById(dto.getUserId()));
        e.setComment(dto.getComment());
        e.setTimeOfComment(dto.getTimeOfComment());
        return e;
    }

    @Override
    public CommentOnCardDTO fromEToD(CommentOnCard e) {
        CommentOnCardDTO dto = new CommentOnCardDTO();
        dto.setCardId(e.getCard().getId());
        dto.setUserId(e.getUser().getId());
        dto.setComment(e.getComment());
        dto.setTimeOfComment(e.getTimeOfComment());
        return dto;
    }

}
