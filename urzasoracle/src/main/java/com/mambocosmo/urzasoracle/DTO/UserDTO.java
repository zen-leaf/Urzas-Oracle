package com.mambocosmo.urzasoracle.DTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UserDTO implements GenericDTO {
    private UUID id;
    private String username;
    // salto la pass
    private String email;
    private String displayName;
    List<? extends GrantedAuthority> authorities;
    private LocalDate registrerDate;
}
