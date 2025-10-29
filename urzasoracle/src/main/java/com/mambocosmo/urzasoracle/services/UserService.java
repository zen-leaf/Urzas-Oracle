package com.mambocosmo.urzasoracle.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.DTO.CardExpansionSetDTO;
import com.mambocosmo.urzasoracle.DTO.UserDTO;
import com.mambocosmo.urzasoracle.converters.CardExpansionSetConverter;
import com.mambocosmo.urzasoracle.converters.UserConverter;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.entities.CardExpansionSet;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.repositories.CardExpansionSetRepository;
import com.mambocosmo.urzasoracle.repositories.UserRepository;

@Service
@Data
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

  
    private final PasswordEncoder passwordEncoder;

    public UrzaUser construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(UrzaUser.class, fromData);
    }
    public boolean registerUser(Map<String, String> userData) {
        if (getUserRepository().findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (getUserRepository().findByEmail(userData.get("email")) != null) {
            return false;
        }

        UrzaUser user = new UrzaUser();
        user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(getPasswordEncoder().encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName")); // Default displayName = username
        user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        user.setRegisterDate(LocalDate.now());

        getUserRepository().save(user);
        return true;
    }

    public boolean registerAdmin(Map<String, String> userData) {
        if (getUserRepository().findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (getUserRepository().findByEmail(userData.get("email")) != null) {
            return false;
        }

        UrzaUser user = new UrzaUser();
        user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(getPasswordEncoder().encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName"));
        user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        user.setRegisterDate(LocalDate.now());

        getUserRepository().save(user);
        return true;
    }

    public UrzaUser findByUsername(String username) {
        return getUserRepository().findByUsername(username);
    }

    public boolean updateUser(String username, Map<String, String> userData) {
        UrzaUser user = getUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }

        if (userData.containsKey("email")) {
            user.setEmail(userData.get("email"));
        }
        if (userData.containsKey("displayName")) {
            user.setDisplayName(userData.get("displayName"));
        }

        getUserRepository().save(user);
        return true;
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        UrzaUser user = getUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }

        if (!getPasswordEncoder().matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(getPasswordEncoder().encode(newPassword));
        getUserRepository().save(user);
        return true;
    }

    public boolean deleteUser(String username) {
        UrzaUser user = getUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }
        getUserRepository().delete(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        return findByUsername(username);
    }
}
