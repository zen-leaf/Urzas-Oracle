package com.mambocosmo.urzasoracle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mambocosmo.urzasoracle.services.CardService;
import com.mambocosmo.urzasoracle.services.UrzaUserService;

import lombok.Data;

@Controller
@Data
public class AppController {

    private final CardService cardService;
    private final UrzaUserService userService;

    @GetMapping("/")
    public String home(Model model) {

        // Carica le prime 150 carte per il mosaico della home
        var topCards = cardService.getRandomPaged(150).getContent();
        model.addAttribute("topCards", topCards);
        model.addAttribute("active", "home");
        return "index"; // index.html
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("active", "login");
        return "login";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // about.html in templates
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("active", "register");
        return "register";
    }

    @GetMapping("/403")
    public String forbidden(Model model) {
        model.addAttribute("active", "error");
        return "403";
    }

    @GetMapping("/error")
    public String error(Model model) {
        model.addAttribute("active", "error");
        return "error";
    }
}
