package com.mambocosmo.urzasoracle.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.UrzaUserDTO;
import com.mambocosmo.urzasoracle.converters.UrzaUserConverter;
import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.repositories.UrzaUserRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper = true)

public class UrzaUserService extends GenericService<UrzaUser, UrzaUserDTO, UrzaUserConverter, UrzaUserRepository>
        implements UserDetailsService {

    private final UrzaUserRepository urzaUserRepository;

    private final PasswordEncoder passwordEncoder;

    public UrzaUser construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(UrzaUser.class, fromData);
    }

    public boolean registerUser(Map<String, String> userData) {
        if (getUrzaUserRepository().findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (getUrzaUserRepository().findByEmail(userData.get("email")) != null) {
            return false;
        }

        UrzaUser user = new UrzaUser();
        // user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(getPasswordEncoder().encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName"));
        user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        user.setRegisterDate(LocalDate.now());

        getUrzaUserRepository().save(user);
        return true;
    }

    public boolean registerAdmin(Map<String, String> userData) {
        if (getUrzaUserRepository().findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (getUrzaUserRepository().findByEmail(userData.get("email")) != null) {
            return false;
        }

        UrzaUser user = new UrzaUser();
        // user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(getPasswordEncoder().encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName"));
        user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        user.setRegisterDate(LocalDate.now());

        getUrzaUserRepository().save(user);
        return true;
    }

    public UrzaUser findByUsername(String username) {
        return getUrzaUserRepository().findByUsername(username);
    }

    public boolean updateUser(String username, Map<String, String> userData) {
        UrzaUser user = getUrzaUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }

        if (userData.containsKey("email")) {
            user.setEmail(userData.get("email"));
        }
        if (userData.containsKey("displayName")) {
            user.setDisplayName(userData.get("displayName"));
        }

        getUrzaUserRepository().save(user);
        return true;
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        UrzaUser user = getUrzaUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }

        if (!getPasswordEncoder().matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(getPasswordEncoder().encode(newPassword));
        getUrzaUserRepository().save(user);
        return true;
    }

    public boolean deleteUser(String username) {
        UrzaUser user = getUrzaUserRepository().findByUsername(username);
        if (user == null) {
            return false;
        }
        getUrzaUserRepository().delete(user);
        return true;
    }

    // METODI PER ADMIN
    public List<UrzaUser> findAllUsers() {
        return getUserRepository().findAll();
    }

    public void deleteUserById(UUID id) {
        getUserRepository().deleteById(id);
    }

    public UrzaUser findById(UUID id) {
        return getUserRepository().findById(id).orElse(null);
    }

    public boolean updateUserByAdmin(UUID userId, String username, String email, String displayName) {
        UrzaUser user = getUserRepository().findById(userId).orElse(null);
        if (user == null || user.isAdmin()) {
            return false;
        }

        // Verifica se username è già usato da un altro utente
        UrzaUser existingUser = getUserRepository().findByUsername(username);
        if (existingUser != null && !existingUser.getId().equals(userId)) {
            return false;
        }

        // Verifica se email è già usata da un altro utente
        UrzaUser existingEmail = getUserRepository().findByEmail(email);
        if (existingEmail != null && !existingEmail.getId().equals(userId)) {
            return false;
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setDisplayName(displayName);
        getUserRepository().save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        return findByUsername(username);
    }
}
