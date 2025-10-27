package com.mambocosmo.urzasoracle.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class AppController {

    // @Value("${app.scuola.nome:Istituto Comprensivo}")
    // private String scuolaNome;

    @GetMapping("/")
    public String home(Model model) {
        // model.addAttribute("scuolaNome", scuolaNome);
        return "/templates/index.html";
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
