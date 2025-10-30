package com.mambocosmo.urzasoracle.controllers;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUsers(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        UrzaUser currentUser = userService.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/";
        }

        List<UrzaUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("active", "admin");

        return "admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable UUID id, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        UrzaUser currentUser = userService.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/";
        }

        
        if (currentUser.getId().equals(id)) {
            return "redirect:/admin/users?error=cannot_delete_self";
        }

        userService.deleteUserById(id);
        return "redirect:/admin/users?success=user_deleted";
    }
}
