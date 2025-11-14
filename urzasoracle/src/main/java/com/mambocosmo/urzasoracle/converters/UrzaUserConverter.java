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

    private final CardCollectionConverter CARDCOLLECTIONCONVERTER;

    @Override
    public UrzaUser fromDToE(UrzaUserDTO dto) {
        UrzaUser u = new UrzaUser();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setDisplayName(dto.getDisplayName());
        u.setAuthorities(dto.getAuthorities());
        u.setRegisterDate(dto.getRegisterDate());
        dto.getUserDecks().forEach(entry -> {
            u.getUserDecks().add(getCARDCOLLECTIONCONVERTER().fromDToE(entry));
        });;
        
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
        dto.setRegisterDate(e.getRegisterDate());
        e.getUserDecks().forEach(entry -> {
            dto.getUserDecks().add(getCARDCOLLECTIONCONVERTER().fromEToD(entry));
        });;
        
        return dto;
    }

}
