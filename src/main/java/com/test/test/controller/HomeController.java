package com.test.test.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.test.service.EtudiantService;
import com.test.test.service.PayerService;

@Controller
public class HomeController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private PayerService payerService;

    // page d'accueil
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Bienvenue sur mon site !");
        
        // niveaux
        Map<String, Long> etudiantsParNiveau = etudiantService.etudiantsParNiveau();
        model.addAttribute("etudiantsParNiveau", etudiantsParNiveau);

        // total étudiants
        long totalEtudiants = etudiantService.totalEtudiants();
        model.addAttribute("totalEtudiants", totalEtudiants);

        // total paiements
        long totalPaye = payerService.totalPaye();
        model.addAttribute("totalPaye", totalPaye);
        
        return "index"; 
    }

    // login
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/"; // authentifié => page d'acueil
        }
        if (error != null) {
            model.addAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
        }
        return "login"; 
    }

    // montants
    // @GetMapping("/montant")
    // public String montant(Model model) {
    //     return "montants"; 
    // }
}
