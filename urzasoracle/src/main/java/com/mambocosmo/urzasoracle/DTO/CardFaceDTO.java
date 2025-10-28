package com.mambocosmo.urzasoracle.DTO;

import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class CardFaceDTO implements GenericDTO{

    private UUID id;
    private String name;
    private String mana_cost;
    private String type_line;
    private String oracle_text;
    private String power;
    private String toughness;
    private String loyalty;
    private String artist;
    private String illustration_id;
    private Map<String, String> image_uris;

}
