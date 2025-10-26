package com.mambocosmo.urzasoracle.entities;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "artists")
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends GenericEntity {
    @Id
    @JsonAlias("artist_id")
    @Column(name = "artist_id")
    private String id;

    @JsonAlias("artist")
    private String artist_name;

}
