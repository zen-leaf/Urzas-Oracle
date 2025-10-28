package com.mambocosmo.urzasoracle.entities;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "card_parts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPart extends GenericEntity {
    @Id
    @Column(name = "part_id")
    private UUID id;
    private String component;
    private String name;
    private String type_line;
    private String uri;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "all_parts")
    // @JoinTable(name = "card_parts_relation", joinColumns = @JoinColumn(name =
    // "part_id"), inverseJoinColumns = @JoinColumn(name = "card_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Card> foundIn;
}
