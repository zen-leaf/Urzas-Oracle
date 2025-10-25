package com.mambocosmo.urzasoracle.entities;

import java.time.LocalDate;

import com.mambocosmo.urzasoracle.misc.SetType;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CardExpansionSet extends GenericEntity{

    @Id
    private String id;
    
    private String code;

    private String name;
    
    private String uri;

    private String scryfall_uri;

    private String search_uri;

    private LocalDate released_at;

    private SetType set_type;

    private String card_count;

    private Boolean digital;

    private Boolean nonfoil_only;

    private Boolean foil_only;

    private String icon_svg_uri;
}
