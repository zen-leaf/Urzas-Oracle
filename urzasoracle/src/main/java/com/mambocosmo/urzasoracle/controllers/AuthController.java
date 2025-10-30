package com.mambocosmo.urzasoracle.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mambocosmo.urzasoracle.services.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Username o password non validi");
        }
        model.addAttribute("active", "login");
        return "login";
    }
     
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("active", "register");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String, String> userData, Model model) {
        String isAdmin = userData.get("isAdmin");

        boolean success;
        if ("true".equalsIgnoreCase(isAdmin)) {
            success = userService.registerAdmin(userData);
        } else {
            success = userService.registerUser(userData);
        }

        if (!success) {
            model.addAttribute("error", "Username o email già esistenti");
            return "register";
        }

        return "redirect:/auth/login?registered=true";
    }
}
