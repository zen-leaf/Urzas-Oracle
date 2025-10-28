package com.mambocosmo.urzasoracle.entities;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "card_faces")
@Data
@ToString(exclude = "faceOfCard")
@EqualsAndHashCode(callSuper = true, exclude = "faceOfCard")

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardFace extends GenericEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    @Column(name = "card_face_id")
    private UUID id;

    // @EmbeddedId
    // private CardFacePK faceid;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ofCard", referencedColumnName = "card_id")
    private Card faceOfCard;

    private String mana_cost;

    private String type_line;

    private String oracle_text;

    @ElementCollection
    @CollectionTable(name = "card_face_colors", joinColumns = @JoinColumn(name = "card_face_id", foreignKey = @ForeignKey(name = "fk_face_colors")))
    @Column(name = "color")
    private List<String> colors;

    private String power;

    private String toughness;

    private String loyalty;

    private String flavor_text;

    private String artist;

    private UUID artist_id;

    private String illustration_id;

    @ElementCollection
    @CollectionTable(name = "card_face_images", joinColumns = @JoinColumn(name = "card_face_id", foreignKey = @ForeignKey(name = "fk_face_images")))
    @MapKeyColumn(name = "image_type")
    @Column(name = "image_uri", length = 500)
    private Map<String, String> image_uris;
}
