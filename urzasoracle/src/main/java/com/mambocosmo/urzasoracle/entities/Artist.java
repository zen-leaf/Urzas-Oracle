package com.mambocosmo.urzasoracle.entities;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "artists")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString()

public class Artist extends GenericEntity {
    @Id
    @Column(name = "artist_id")
    private UUID id;

    @JsonAlias("artist")
    @Column(name = "artist_name")
    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "artistRef")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Card> illustratedCards;

}
