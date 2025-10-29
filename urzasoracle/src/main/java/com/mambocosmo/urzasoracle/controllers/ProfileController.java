package com.mambocosmo.urzasoracle.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.entities.User;
import com.mambocosmo.urzasoracle.services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("active", "profile");
        model.addAttribute("userCollectionCount", 0);
        model.addAttribute("userDecksCount", 0);

        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@RequestParam Map<String, String> userData, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        boolean success = userService.updateUser(principal.getName(), userData);
        if (!success) {
            return "redirect:/profile?error=true";
        }

        return "redirect:/profile?success=true";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword,
            Principal principal) {
        
        if (principal == null) {
            return "redirect:/auth/login";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return "redirect:/profile?error=password_mismatch";
        }

        boolean success = userService.changePassword(principal.getName(), currentPassword, newPassword);
        if (!success) {
            return "redirect:/profile?error=invalid_password";
        }

        return "redirect:/profile?success=password_changed";
    }

    @GetMapping("/delete")
    public String deleteAccount(Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        userService.deleteUser(principal.getName());
        return "redirect:/auth/logout";
    }
}
