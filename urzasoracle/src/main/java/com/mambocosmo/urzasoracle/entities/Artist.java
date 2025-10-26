package com.mambocosmo.urzasoracle.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    private String id;

    @JsonAlias("artist")
    private String artist_name;
    @ManyToMany
    @JoinTable(
        name = "artist_cards",
        joinColumns = {
            @JoinColumn(name = "artistref_id") }, 
            inverseJoinColumns = {
            @JoinColumn(name = "cardref_id") })
    Set<Card> illustratedCards;

}
