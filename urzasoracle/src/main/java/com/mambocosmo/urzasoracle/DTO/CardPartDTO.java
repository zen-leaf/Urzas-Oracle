package com.mambocosmo.urzasoracle.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class CardPartDTO implements GenericDTO {
    private UUID id;
    private String name;
    private String type_line;
    private String component;
    private String uri;

}
