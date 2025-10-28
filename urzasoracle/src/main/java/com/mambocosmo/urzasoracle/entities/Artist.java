package com.mambocosmo.urzasoracle.entities;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAlias;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@EqualsAndHashCode(callSuper = true,exclude = "illustratedCards")
@ToString(exclude = "illustratedCards")

public class Artist extends GenericEntity {
    @Id
    @Column(name = "artist_id")
    private UUID id;

    @JsonAlias("artist")
    @Column(name = "artist_name")
    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "artistRef")
    // @JoinTable(name = "artist_cards", joinColumns = {
    // @JoinColumn(name = "artistref_id") }, inverseJoinColumns = {
    // @JoinColumn(name = "cardref_id") })
    Set<Card> illustratedCards;

}
