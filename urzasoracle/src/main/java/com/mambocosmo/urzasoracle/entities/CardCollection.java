package com.mambocosmo.urzasoracle.entities;

import java.util.List;
import java.util.UUID;

import com.mambocosmo.urzasoracle.misc.enums.Format;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CardCollection extends GenericEntity {

    @Id
    private UUID id;

    private User owner;

    private String name;

    private String description;

    private List<Format> legalIn;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardInDeck> cardList;

}
