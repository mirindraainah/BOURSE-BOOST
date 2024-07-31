package com.test.test.controller;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.test.model.Etudiant;
import com.test.test.model.Payer;
import com.test.test.service.EtudiantService;
import com.test.test.service.PayerService;

@Controller
public class RetardController {

    @Autowired
    private PayerService payerService;

    @GetMapping("/retardataires")
    public String getAllRetardataires(Model model) {
  
        List<Etudiant> retardataires = payerService.getRetardataires();

        model.addAttribute("retardataires", retardataires);
        model.addAttribute("retardatairesCount", retardataires.size());

        return "retardataires";
    }
    
    @GetMapping("/retardataires/{mois}")
    @ResponseBody
    public List<Etudiant> getRetardatairesByMonth(@PathVariable("mois") Month mois) {
        return payerService.getRetardatairesForMonth(mois);
    }
}
