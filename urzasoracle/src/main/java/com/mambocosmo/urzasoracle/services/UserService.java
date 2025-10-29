package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.DTO.UserDTO;
import com.mambocosmo.urzasoracle.converters.CardExpansionSetConverter;
import com.mambocosmo.urzasoracle.converters.UserConverter;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.repositories.CardExpansionSetRepository;
import com.mambocosmo.urzasoracle.repositories.UserRepository;

import lombok.Data;

@Service
@Data
public class UserService extends GenericService<UrzaUser, UserDTO, UserConverter, UserRepository> {
    public UrzaUser construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(UrzaUser.class, fromData);
    }

    public UrzaUser getByUsername(String name) {
        return getREPOSITORY().findByUsername(name);
    }

}
