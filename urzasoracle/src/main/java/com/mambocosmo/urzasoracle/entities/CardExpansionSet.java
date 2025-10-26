package com.mambocosmo.urzasoracle.entities;

import java.util.Set;

import com.mambocosmo.urzasoracle.misc.enums.SetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ExpansionSets")
@Data
@EqualsAndHashCode(callSuper = true)
public class CardExpansionSet extends GenericEntity {

    @Id
    @Column(name = "expansion_id")
    private String id;

    private String code;

    private String name;

    private String uri;

    private String scryfall_uri;

    private String search_uri;

    private String released_at;

    private SetType set_type;

    private String card_count;

    private Boolean digital;

    private Boolean nonfoil_only;

    private Boolean foil_only;

    private String icon_svg_uri;

    @OneToMany(mappedBy = "expansion", fetch = FetchType.LAZY)
    private Set<Card> cardList;
}
