package com.mambocosmo.urzasoracle.converters;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.UrzaUserDTO;
import com.mambocosmo.urzasoracle.entities.UrzaUser;

import lombok.Data;

@Service
@Data
public class UrzaUserConverter implements GenericConverter<UrzaUser, UrzaUserDTO> {

    @Override
    public UrzaUser fromDToE(UrzaUserDTO dto) {
        UrzaUser u = new UrzaUser();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setDisplayName(dto.getDisplayName());
        u.setAuthorities(dto.getAuthorities());
        u.setRegisterDate(dto.getRegistrerDate());
        u.setUserDecks(dto.getUserDecks());

        return u;
    }

    @Override
    public UrzaUserDTO fromEToD(UrzaUser e) {
        UrzaUserDTO dto = new UrzaUserDTO();
        dto.setId(e.getId());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setDisplayName(e.getDisplayName());
        dto.setAuthorities(e.getAuthorities());
        System.out.println(e.getRegisterDate());
        dto.setRegistrerDate(e.getRegisterDate());
        dto.setUserDecks(e.getUserDecks());

        return dto;
    }

}
