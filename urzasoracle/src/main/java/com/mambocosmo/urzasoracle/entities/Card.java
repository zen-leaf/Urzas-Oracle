package com.mambocosmo.urzasoracle.entities;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mambocosmo.urzasoracle.misc.enums.BorderColor;
import com.mambocosmo.urzasoracle.misc.enums.Format;
import com.mambocosmo.urzasoracle.misc.enums.Frame;
import com.mambocosmo.urzasoracle.misc.enums.WURBG;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
// @JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends GenericEntity {

    @Id
    private String id;

    private String oracle_id;

    private String name;

    private String lang;

    private String released_at;

    private String uri;

    private String scryfall_uri;

    private String layout;

    private Boolean highres_image;

    private String image_status;

    private Map<String, String> image_uris;

    /*
     * USUALLY MISSING IF @{card_faces} is not null
     */
    private String mana_cost;

    private String cmc;

    private String type_line;

    private String oracle_text;

    private String flavor_text;

    private List<WURBG> colors;

    private List<WURBG> color_identity;

    private List<String> keywords;

    // private List<CardFace> card_faces;

    private List<String> produced_mana;

    private Map<Format, String> legalities;

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

    private String card_back_id;

    @JsonUnwrapped // Json mapper will create an instance of this obj and populate its fields
    private Artist artistRef;

    private String illustration_id;

    private BorderColor border_color;

    private Frame frame;

    private Boolean full_art;

    private Boolean textless;

    private Boolean booster;
}
