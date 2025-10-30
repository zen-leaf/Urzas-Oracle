package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.UserDTO;
import com.mambocosmo.urzasoracle.entities.UrzaUser;

import lombok.Data;

@Service
@Data
public class UserConverter implements GenericConverter<UrzaUser, UserDTO> {

    @Override
    public UrzaUser fromDToE(UserDTO dto) {
        UrzaUser u = new UrzaUser();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setDisplayName(dto.getDisplayName());
        u.setAuthorities(dto.getAuthorities());
        u.setRegisterDate(dto.getRegistrerDate());

        return u;
    }

    @Override
    public UserDTO fromEToD(UrzaUser e) {
        UserDTO dto = new UserDTO();
        dto.setId(e.getId());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setDisplayName(e.getDisplayName());
        dto.setAuthorities(e.getAuthorities());
        dto.setRegistrerDate(e.getRegisterDate());
        return dto;
    }

}
