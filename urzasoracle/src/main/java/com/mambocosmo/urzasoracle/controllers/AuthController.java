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
    public String loginPage(){
            return "login";
    }
     
    @GetMapping("/register")
    public String register(){
        return "/register";
    }

    
   @PostMapping("/register")
    public String registerUser(@RequestParam Map<String,String> userData, Model model) {
    String role = userData.get("role");

    boolean success;
    if ("admin".equalsIgnoreCase(role)) {
        success = userService.registerAdmin(userData);
    } else {
        success = userService.registerUser(userData);
    }

    if (!success) {
        return "redirect:/error";
    }

    return "redirect:/auth/login";
}
       
}