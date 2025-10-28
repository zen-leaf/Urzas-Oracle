package com.mambocosmo.urzasoracle.entities;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = "foundIn")
@ToString(callSuper = true, exclude = "foundIn")
@Entity
@Table(name = "card_parts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPart extends GenericEntity {
    @Id
    private UUID id;
    private String component;
    private String name;
    private String type_line;
    private String uri;

    @ManyToMany
    @JoinTable(name = "card_part_relation", joinColumns = {
            @JoinColumn(name = "part_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "cardref_id") })
    private Set<Card> foundIn;
}
