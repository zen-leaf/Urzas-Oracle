package com.mambocosmo.urzasoracle.DTO;

import java.util.Map;
import java.util.UUID;

import lombok.Data;

@Data
public class CardDTO implements GenericDTO {
    
    private UUID id;

    private String name;

    private Map<String, String> images;

}
