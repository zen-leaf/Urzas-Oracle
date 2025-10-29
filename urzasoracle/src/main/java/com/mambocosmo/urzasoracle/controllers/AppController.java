package com.mambocosmo.urzasoracle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html in templates
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // register.html in templates
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "register"; // register.html in templates
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
