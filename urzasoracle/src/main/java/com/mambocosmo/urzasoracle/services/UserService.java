package com.mambocosmo.urzasoracle.services;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.entities.User;
import com.mambocosmo.urzasoracle.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(Map<String, String> userData) {
        if (userRepository.findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (userRepository.findByEmail(userData.get("email")) != null) {
            return false;
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(passwordEncoder.encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName")); // Default displayName = username
        user.setRole("USER");
        user.setRegisterDate(LocalDate.now());

        userRepository.save(user);
        return true;
    }

    public boolean registerAdmin(Map<String, String> userData) {
        if (userRepository.findByUsername(userData.get("username")) != null) {
            return false;
        }
        if (userRepository.findByEmail(userData.get("email")) != null) {
            return false;
        }

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userData.get("username"));
        user.setPassword(passwordEncoder.encode(userData.get("password")));
        user.setEmail(userData.get("email"));
        user.setDisplayName(userData.get("displayName"));
        user.setRole("ADMIN");
        user.setRegisterDate(LocalDate.now());

        userRepository.save(user);
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean updateUser(String username, Map<String, String> userData) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        if (userData.containsKey("email")) {
            user.setEmail(userData.get("email"));
        }
        if (userData.containsKey("displayName")) {
            user.setDisplayName(userData.get("displayName"));
        }

        userRepository.save(user);
        return true;
    }

    public boolean changePassword(String username, String currentPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }
}
