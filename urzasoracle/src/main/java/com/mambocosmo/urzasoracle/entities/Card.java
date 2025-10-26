package com.mambocosmo.urzasoracle.entities;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mambocosmo.urzasoracle.misc.enums.BorderColor;
import com.mambocosmo.urzasoracle.misc.enums.Format;
import com.mambocosmo.urzasoracle.misc.enums.Frame;
import com.mambocosmo.urzasoracle.misc.enums.WURBG;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cards")
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

    @ElementCollection
    @CollectionTable(name = "card_images", joinColumns = @JoinColumn(name = "card_id"))
    @MapKeyColumn(name = "image_type")
    @JsonUnwrapped
    private Map<String, String> image_uris;

    /*
     * USUALLY MISSING IF @{card_faces} is not null
     */
    private String mana_cost;

    private String cmc;

    private String type_line;

    private String oracle_text;

    private String flavor_text;

    @ElementCollection
    @CollectionTable(name = "card_color_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<WURBG> colors;

    @ElementCollection
    @CollectionTable(name = "card_color_identity_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<WURBG> color_identity;
    @ElementCollection
    @CollectionTable(name = "keyword_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> keywords;

    @JsonUnwrapped
    @ElementCollection
    @CollectionTable(name = "card_faces_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<Card> card_faces;

    private Set<String> produced_mana;

    @ElementCollection
    @CollectionTable(name = "card_legal_formats", joinColumns = @JoinColumn(name = "card_id"))
    @MapKeyColumn(name = "format")
    private Map<Format, String> legalities;

    private Boolean reserverd;

    private Boolean game_changer;

    private Boolean foil;

    private Boolean nonfoil;

    @ElementCollection
    @CollectionTable(name = "card_finishes", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> finishes;

    private Boolean oversized;

    private Boolean promo;

    private Boolean reprint;

    private Boolean variation;

    @ManyToOne
    @JoinColumn(name = "set_id", referencedColumnName = "expansion_id")
    private CardExpansionSet expansion;

    private String ulings_uri;

    private String prints_search_uri;

    private String collector_number;

    private Boolean digital;

    private String rarity;

    private String card_back_id;

    @JsonUnwrapped // Json mapper will create an instance of this obj and populate its fields
    @ManyToOne
    @JoinColumn(name = "artist_ids", referencedColumnName = "artist_id")
    private Artist artistRef;

    private String illustration_id;

    private BorderColor border_color;

    private Frame frame;

    private Boolean full_art;

    private Boolean textless;

    private Boolean booster;
    // printed_text - String
    // flavor_name - String
    // hand_modifier - String
    // toughness - String
    // watermark - String
    // loyalty - String
    // attraction_lights - Set<String> (le luci accese 12345 tipo)
    // reserved - Boolean
    // all_parts - Set<Card> ?
    // color_indicator - Set<WURBG>
    // life_modifier - String
    // frame_effects - Set<String>
    // printed_name - String
    // power - String
    // printed_type_line - String
    // variation_of - String

}
