package com.mambocosmo.urzasoracle.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.DTO.UserDTO;
import com.mambocosmo.urzasoracle.converters.CardExpansionSetConverter;
import com.mambocosmo.urzasoracle.converters.UserConverter;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.User;
import com.mambocosmo.urzasoracle.repositories.CardExpansionSetRepository;
import com.mambocosmo.urzasoracle.repositories.UserRepository;

import lombok.Data;

@Service
@Data
public class UserService  extends GenericService<User, UserDTO, UserConverter, UserRepository>{


   @Override
    public User construct(Map<String, String> userData) {
        return construct(userData, "USER");
    }

    public User construct(Map<String, String> userData, String role) {
        User user = new User();
        user.setUsername(userData.get("username"));
        user.setPassword(userData.get("password")); // password in chiaro
        user.setRole(role); // "ADMIN" o "USER"
        return user;
    }

    public boolean registerUser(Map<String, String> userData) {
        UUID id = UUID.fromString(userData.get("id"));
        if (getREPOSITORY().existsById(id)) return false;

        User user = construct(userData, "USER");
        getREPOSITORY().save(user);
        return true;
    }

    public boolean registerAdmin(Map<String, String> userData) {
        UUID id = UUID.fromString(userData.get("id"));
        if (getREPOSITORY().existsById(id)) return false;

        User user = construct(userData, "ADMIN");
        getREPOSITORY().save(user);
        return true;
    }
}

