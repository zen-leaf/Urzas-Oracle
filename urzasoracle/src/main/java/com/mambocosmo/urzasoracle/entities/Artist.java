package com.mambocosmo.urzasoracle.entities;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "artists")
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends GenericEntity {
    @Id
    @Column(name = "artist_id")
    private UUID id;

    @JsonAlias("artist")
    @Column(name = "artist_name")
    private String name;

    @ManyToMany
    @JoinTable(name = "artist_cards", joinColumns = {
            @JoinColumn(name = "artistref_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "cardref_id") })
    Set<Card> illustratedCards;

}
