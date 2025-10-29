package com.mambocosmo.urzasoracle.entities;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.mambocosmo.urzasoracle.misc.enums.Format;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CardCollection extends GenericEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    @Column(name = "deck_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ofUser")
    private User owner;

    private String name;

    private String description;

    private Format mainDeckFormat;

    //candidate to remove
    @ElementCollection
    @CollectionTable(name = "deck_legal_formats", joinColumns = @JoinColumn(name = "deck_id"))
    @MapKeyColumn(name = "format")
    private Map<Format, String> legalIn;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardInDeck> cardList;

}
