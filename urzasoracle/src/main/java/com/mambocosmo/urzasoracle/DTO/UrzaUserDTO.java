package com.mambocosmo.urzasoracle.DTO;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UrzaUserDTO implements GenericDTO {
    private UUID id;
    private String username;
    // salto la pass
    private String email;
    private String displayName;
    List<? extends GrantedAuthority> authorities;
    private Date registerDate;
    private Set<CardCollectionDTO> userDecks;
}
