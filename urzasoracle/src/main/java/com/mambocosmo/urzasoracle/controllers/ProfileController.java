package com.mambocosmo.urzasoracle.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.entities.UrzaUser;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Controller
@Data
@RequestMapping("/profile")
public class ProfileController {

    private final UrzaUserService userService;

    @GetMapping
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        UrzaUser user = userService.findByUsername(principal.getName());
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("active", "profile");
        model.addAttribute("userCollectionCount", 0);
        model.addAttribute("userDecksCount", 0);

        // Se l'utente è admin, mostra la lista di tutti gli utenti
        if (user.isAdmin()) {
            List<UrzaUser> allUsers = userService.findAllUsers();
            model.addAttribute("allUsers", allUsers);
        }

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
        UrzaUser myUser = getUserService().findByUsername(principal.getName());
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken newAUTH = new UsernamePasswordAuthenticationToken(myUser, currentAuth,
                myUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAUTH);

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

    // Admin: Elimina utente (solo non-admin)
    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable UUID id, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }

        UrzaUser currentUser = userService.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/profile?error=not_authorized";
        }

        // Trova l'utente da eliminare
        UrzaUser userToDelete = userService.findById(id);
        if (userToDelete == null) {
            return "redirect:/profile?error=user_not_found";
        }

        // Impedisci all'admin di eliminare se stesso
        if (currentUser.getId().equals(id)) {
            return "redirect:/profile?error=cannot_delete_self";
        }

        // Impedisci di eliminare altri admin
        if (userToDelete.isAdmin()) {
            return "redirect:/profile?error=cannot_delete_admin";
        }

        userService.deleteUserById(id);
        return "redirect:/profile?success=user_deleted";
    }

    // Admin: Modifica utente (solo non-admin)
    @PostMapping("/edit-user/{id}")
    public String editUser(
            @PathVariable UUID id,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String displayName,
            Principal principal) {

        if (principal == null) {
            return "redirect:/auth/login";
        }

        UrzaUser currentUser = userService.findByUsername(principal.getName());
        if (currentUser == null || !currentUser.isAdmin()) {
            return "redirect:/profile?error=not_authorized";
        }

        // Trova l'utente da modificare
        UrzaUser userToEdit = userService.findById(id);
        if (userToEdit == null) {
            return "redirect:/profile?error=user_not_found";
        }

        // Impedisci di modificare admin
        if (userToEdit.isAdmin()) {
            return "redirect:/profile?error=cannot_edit_admin";
        }

        boolean success = userService.updateUserByAdmin(id, username, email, displayName);
        if (!success) {
            return "redirect:/profile?error=update_failed";
        }

        return "redirect:/profile?success=user_updated";
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
