package com.mambocosmo.urzasoracle.DTO;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO implements GenericDTO{
    private UUID id;
    private String username;
    //salto la pass 
    private String email;
    private String displayName;
    private String role;
    private LocalDate registrerDate;
}
