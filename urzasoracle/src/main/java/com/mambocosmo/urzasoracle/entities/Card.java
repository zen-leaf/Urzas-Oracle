package com.mambocosmo.urzasoracle.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mambocosmo.urzasoracle.misc.enums.AttractionLight;
import com.mambocosmo.urzasoracle.misc.enums.BorderColor;
import com.mambocosmo.urzasoracle.misc.enums.Format;
import com.mambocosmo.urzasoracle.misc.enums.Frame;
import com.mambocosmo.urzasoracle.misc.enums.WURBG;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cards")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends GenericEntity {

    @Id
    @Column(name = "card_id")
    private UUID id;

    private UUID oracle_id;

    private String name = "";

    private String lang = "";

    private String released_at = "";

    private String uri = "";

    private String scryfall_uri = "";

    private String layout = "";

    private Boolean highres_image = false;

    private String image_status = "";

    @ElementCollection
    @CollectionTable(name = "card_images", joinColumns = @JoinColumn(name = "card_id"))
    @MapKeyColumn(name = "image_type")
    @JsonUnwrapped
    private Map<String, String> image_uris;

    /*
     * USUALLY MISSING IF @{card_faces} is not null
     */
    private String mana_cost = "";

    private String cmc = "";

    private String type_line = "";

    @Column(length = 2048)
    private String oracle_text = "";

    @Column(length = 512)
    private String flavor_text = "";

    @ElementCollection
    @CollectionTable(name = "card_color_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<WURBG> colors;

    @ElementCollection
    @CollectionTable(name = "card_color_identity_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<WURBG> color_identity;

    @ElementCollection
    @CollectionTable(name = "card_keyword_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> keywords;

    @OneToMany(mappedBy = "faceOfCard", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    // @JsonUnwrapped
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardFace> card_faces;

    @JsonSetter("card_faces")
    public void setCard_faces(List<CardFace> faces) {
        this.card_faces = faces;
        if (faces != null && !faces.isEmpty()) {
            for (CardFace face : faces) {

                face.setFaceOfCard(this);

            }
        }
    }

    @ElementCollection
    @CollectionTable(name = "card_produced_mana", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> produced_mana;

    @ElementCollection
    @CollectionTable(name = "card_legal_formats", joinColumns = @JoinColumn(name = "card_id"))
    @MapKeyColumn(name = "format")
    private Map<Format, String> legalities;
    // creare un oggetto dedicato che setta un field per chiave al posto di avere
    // una mappa
    // LOW PRIORITY

    private Boolean reserverd = false;

    private Boolean game_changer = false;

    private Boolean foil = false;

    private Boolean nonfoil = false;

    @ElementCollection
    @CollectionTable(name = "card_finishes", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> finishes;

    private Boolean oversized = false;

    private Boolean promo = false;

    private Boolean reprint = false;

    private Boolean variation = false;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "set_id", referencedColumnName = "expansion_id")
    private CardExpansionSet expansion;

    @JsonProperty("set_id")
    public void setCardExpansion(UUID id) {
        CardExpansionSet ces = new CardExpansionSet();
        ces.setId(id);
        this.expansion = ces;
    }

    private String rulings_uri = "";

    private String prints_search_uri = "";

    private String collector_number = "";

    private Boolean digital = false;

    private String rarity = "";

    private UUID card_back_id;

    @ManyToMany(cascade = { CascadeType.ALL }) // mapped by foundIn in
                                               // CardPart
    @JoinTable(name = "card_parts_relation", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "part_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CardPart> all_parts;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "card_artists", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "artist_id"))
    private List<Artist> artistRef;

    @Transient
    private List<String> tempname;

    @JsonProperty("artist_ids")
    public void setArtistID(List<UUID> ids) {
        artistRef = new ArrayList<>();
        if (artistRef != null && !ids.isEmpty()) {
            for (UUID string : ids) {
                Artist a = new Artist();
                a.setId(string);
                artistRef.add(a);
            }
            if (tempname != null) {
                int index = 0;
                for (Artist a : artistRef) {
                    a.setName(tempname.get(index).strip());
                    index++;
                }
            }
        }
    }

    @JsonProperty("artist_id")
    public void setSingleArtist(UUID id) {
        setArtistID(List.of(id));
    }

    @JsonProperty("artist")
    public void setArtistName(String name) {
        List<String> tempString = Arrays.asList(name.split("&"));
        if (artistRef == null) {
            tempname = tempString;

        } else if (artistRef.size() == tempString.size()) {
            int index = 0;
            for (Artist a : artistRef) {
                a.setName(tempString.get(index));
                index++;
            }
        }

    }

    private String illustration_id = "";

    private BorderColor border_color = BorderColor.NONE;

    private Frame frame = Frame.NONE;

    private Boolean full_art = false;

    private Boolean textless = false;

    private Boolean booster = false;

    @Column(length = 2048)
    private String printed_text = "";

    private String flavor_name = "";

    private String hand_modifier = "";

    private String toughness = "";

    private String watermark = "";

    private String loyalty = "";

    @ElementCollection
    @CollectionTable(name = "card_attraction_lights_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<AttractionLight> attraction_lights;

    private Boolean reserved = false;

    @ElementCollection
    @CollectionTable(name = "card_color_indicator_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<WURBG> color_indicator;

    private String life_modifier = "";

    @ElementCollection
    @CollectionTable(name = "card_frame_effects_table", joinColumns = @JoinColumn(name = "card_id"))
    private Set<String> frame_effects;

    private String printed_name = "";

    private String power = "";

    private String printed_type_line = "";

    private String variation_of = "";

}
