package com.mambocosmo.urzasoracle.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class ArtistDTO implements GenericDTO {

    private UUID id;
    private String artist_name;

}
