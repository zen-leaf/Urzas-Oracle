package com.mambocosmo.urzasoracle.entities;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class CardFace {
    private String object;
    private String name;
    private String mana_cost;
    private String type_line;
    private String oracle_text;
    private List<String> colors;
    private String power;
    private String toughness;
    private String flavor_text;
    private String artist;
    private String artist_id;
    private String illustration_id;
    private Map<String, String> image_uris;
}
