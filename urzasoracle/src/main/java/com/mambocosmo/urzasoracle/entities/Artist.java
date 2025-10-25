package com.mambocosmo.urzasoracle.entities;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Artist extends GenericEntity{
    @JsonAlias("artist_id")
    private String id;

    @JsonAlias("artist")
    private String artist_name;

}
