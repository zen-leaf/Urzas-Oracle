package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.UserDTO;
import com.mambocosmo.urzasoracle.entities.User;

import lombok.Data;
@Service
@Data
public class UserConverter  implements GenericConverter <User, UserDTO>{

    @Override
    public User fromDToE(UserDTO dto) {
       User u = new User();
       u.setId(dto.getId());
       u.setUsername(dto.getUsername());
       u.setEmail(dto.getEmail());
       u.setDisplayName(dto.getDisplayName());
       u.setRole(dto.getRole());
       u.setRegisterDate(dto.getRegistrerDate());

       return u;
    }

    @Override
    public UserDTO fromEToD(User e) {
        UserDTO dto = new UserDTO();
        dto.setId( e.getId());
        dto.setUsername(e.getUsername());
        dto.setEmail(e.getEmail());
        dto.setDisplayName(e.getDisplayName());
        dto.setRole(e.getRole());
        dto.setRegistrerDate(e.getRegisterDate());
        return dto;
    } 
    
}
