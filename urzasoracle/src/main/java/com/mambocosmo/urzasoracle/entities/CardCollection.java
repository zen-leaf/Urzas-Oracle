package com.mambocosmo.urzasoracle.entities;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public abstract class CardCollection extends GenericEntity{

    @Id
    private String id;


    private User owner;


    private String name;


    private String description;

}
