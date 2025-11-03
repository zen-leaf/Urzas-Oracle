package com.mambocosmo.urzasoracle.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mambocosmo.urzasoracle.entities.CommentOnCard;
// import com.mambocosmo.urzasoracle.entities.CommentOnCardPK;

@Repository
public interface CommentOnCardRepository extends JpaRepository<CommentOnCard, UUID>, JpaSpecificationExecutor<CommentOnCard>{

    @Query("SELECT coc FROM CommentOnCard coc WHERE coc.card.id = :cardId")
    List<CommentOnCard> findByCardId(@Param("cardId") UUID cardId);

}
