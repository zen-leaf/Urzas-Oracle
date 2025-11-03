package com.mambocosmo.urzasoracle.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.DTO.CommentOnCardDTO;
import com.mambocosmo.urzasoracle.converters.CommentOnCardConverter;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CommentOnCard;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.repositories.CommentOnCardRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = false)
public class CommentOnCardService
        extends GenericService<CommentOnCard, CommentOnCardDTO, CommentOnCardConverter, CommentOnCardRepository> {

    private final CommentOnCardRepository COMMENTONCARDREPOSITORY;
    private final CommentOnCardConverter COMMENTONCARDCONVERTER;
    private final UrzaUserService URZAUSERSERVICE;
    private final CardService CARDSERVICE;
    
    @Override
    public CommentOnCard construct(Map<String, String> fromData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'construct'");
    }
    
    public Page<CommentOnCardDTO> getCommentsByCard(UUID idCard, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "time_of_comment"));
        Page<CommentOnCard> pageOut = getCOMMENTONCARDREPOSITORY().findByCardIdPage(idCard, pageable);
        // pageOut = getCOMMENTONCARDCONVERTER().fromEToD(null)
        return pageOut.map(coc -> getCOMMENTONCARDCONVERTER().fromEToD(coc));
    }

    public List<CommentOnCardDTO> getCommentsByCard(UUID idCard) {
        List<CommentOnCard> tempList = getCOMMENTONCARDREPOSITORY().findByCardIdList(idCard);
        return tempList.stream().map(coc -> getCOMMENTONCARDCONVERTER().fromEToD(coc)).toList();
    }

    public List<CommentOnCard> getCommentsByCardEntity(UUID idCard) {
        return getCOMMENTONCARDREPOSITORY().findByCardIdList(idCard);
    }


    public CommentOnCardDTO createCommentOnCard(UUID userId, UUID cardId, String commentText) {
        UrzaUser user = getURZAUSERSERVICE().getEntityByID(userId);
        if (user == null)
            return null;

        CommentOnCard coc = new CommentOnCard();
        coc.setCard(getCARDSERVICE().getByIdEntity(cardId));
        coc.setUser(getURZAUSERSERVICE().getEntityByID(userId));
        coc.setComment(commentText);

        save(coc);
        return getCONVERTER().fromEToD(coc);
    }

    public CommentOnCardDTO updateCommentOnCard(UUID commentId,
    // UUID userId, UUID cardId, 
    String commentText) {
        CommentOnCard coc = getREPOSITORY().findById(commentId).orElse(null);
        if (coc == null)
            return null;

        coc.setComment(commentText);
        coc.setTimeOfComment(Date.valueOf(LocalDate.now()));
        save(coc);

        return getCOMMENTONCARDCONVERTER().fromEToD(coc);
    }

    public boolean deleteCommentOnCard(UUID commentId
    //, UUID userId, UUID cardId
    ) {
        CommentOnCard coc = getREPOSITORY().findById(commentId).orElse(null);
        if (coc == null)
            return false;

        return delete(commentId);
    }

}
