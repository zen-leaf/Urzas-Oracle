package com.mambocosmo.urzasoracle.models;

import java.util.List;
import java.util.Map;

import com.mambocosmo.urzasoracle.entities.IMappable;

import lombok.Data;

@Data
public class CardFace implements IMappable {
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
    private Map<String,String> image_uris;
}
