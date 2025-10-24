package com.mambocosmo.urzasoracle.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends GenericEntity{

    @Id
    private String id;


    private String username;

    
    private String password;


    private String email;


    private String displayName;


    private LocalDate registerDate;

}
