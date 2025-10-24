package com.mambocosmo.urzasoracle.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.mambocosmo.urzasoracle.misc.BorderColor;
import com.mambocosmo.urzasoracle.misc.Format;
import com.mambocosmo.urzasoracle.misc.Frame;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Card extends GenericEntity{

    @Id
    private String id;

    private String oracle_id;

    private String name;

    private String lang;

    private LocalDate released_at;

    private String uri;

    private String scryfall_uri;

    private String layout;

    private Map<String,String> image_uris;

    private String cmc;

    private String type_line;

    private String oracle_text;

    private List<String> colors;
    
    private List<String> color_identity;
    
    private List<String> keywords;
    
    private List<String> produced_mana;
    
    private List<Format> legalities;

    private Boolean reserverd;

    private Boolean game_changer;

    private Boolean foil;
    
    private Boolean nonfoil;

    private List<String> finishes;

    private Boolean oversized;

    private Boolean promo;

    private Boolean reprint;

    private Boolean variation;

    private CardExpansionSet expansion;

    private String ulings_uri;

    private String prints_search_uri;

    private String collector_number;

    private Boolean digital;

    private String rarity;

    private Artist artist;

    private String illustration_id;

    private BorderColor border_color;

    private Frame frame;

    private Boolean full_art;

    private Boolean textless;
    
    private Boolean booster;
}
