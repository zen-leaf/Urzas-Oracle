package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardDTO;
import com.mambocosmo.urzasoracle.DTO.CommentOnCardDTO;
import com.mambocosmo.urzasoracle.converters.CardConverter;
import com.mambocosmo.urzasoracle.converters.CommentOnCardConverter;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.repositories.CommentOnCardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentOnCardService
        extends GenericService<CommentOnCard, CommentOnCardDTO, CommentOnCardConverter, CommentOnCardRepository> {

    private final CommentOnCardRepository COMMENTONCARDREPOSITORY;

    public List<CommentOnCard> getCommentsByCard(UUID idCard) {
        return getCOMMENTONCARDREPOSITORY().findByCardId(idCard);
    }

    @Override
    public CommentOnCard construct(Map<String, String> fromData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'construct'");
    }

}
