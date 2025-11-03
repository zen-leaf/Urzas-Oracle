package com.mambocosmo.urzasoracle.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommentOnCard extends GenericEntity{

    // @EmbeddedId
    // private CommentOnCardPK id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    @Column(name = "comment_id")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UrzaUser user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "card_id")
    private Card card;


    private Date timeOfComment = Date.valueOf(LocalDate.now());

    private String comment;

    // public CommentOnCard(Card card, UrzaUser user, String comment) {
    //     this.comment = comment;
    //     this.id = new CommentOnCardPK(card,user);
    // }

    // public Card getCard() {
    //     return getId().getCard();
    // }
}
