package com.mambocosmo.urzasoracle.DTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class CardDTO implements GenericDTO {

    private UUID id;
    private String name;
    private String manaCost;
    private String typeline;
    private Map<String, String> images;
    private CardFaceDTO backFace;
    private String released_at;
    private String rarity;
    private String flavorText;
    private String oracleText;
    private String collectorNumber;

    private CardExpansionSetDTO expansionSet;

    private List<UUID> artistIds;
    private List<String> artistNames;
    private List<CardPartDTO> cardParts;
    private List<CardFaceDTO> cardFaces;
    private List<String> colorIdentity;
    private Map<String, String> legalities;

}
