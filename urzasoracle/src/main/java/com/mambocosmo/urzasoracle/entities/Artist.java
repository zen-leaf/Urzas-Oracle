package com.mambocosmo.urzasoracle.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class Artist extends GenericEntity{

    private String string;

    private String artist_name;

}
