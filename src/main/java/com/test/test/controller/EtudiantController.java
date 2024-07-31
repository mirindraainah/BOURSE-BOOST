package com.test.test.controller;

import com.test.test.model.Etudiant;
import com.test.test.model.Montant;
import com.test.test.service.EtudiantService;
import com.test.test.service.MontantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private MontantService montantService;

    @GetMapping
    public String getAllEtudiants(Model model) {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        List<Montant> niveaux = montantService.getAllMontants();
        List<String> institutions = etudiantService.getAllInstitutions();
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("etudiant", new Etudiant());
        model.addAttribute("niveaux", niveaux);
        model.addAttribute("institutions", institutions);

        // total étudiants
        long totalEtudiants = etudiantService.totalEtudiants();
        model.addAttribute("totalEtudiants", totalEtudiants);

        model.addAttribute("etudiantCount", etudiants.size());

        return "etudiants";
    }

    @PostMapping("/add")
    public String addEtudiant(@ModelAttribute Etudiant etudiant, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "redirect:/etudiants";
        }
        etudiantService.saveOrUpdateEtudiant(etudiant);
        redirectAttributes.addFlashAttribute("added", true);
        return "redirect:/etudiants";
    }

    @PostMapping("/update")
    public String updateEtudiant(@ModelAttribute Etudiant etudiant, RedirectAttributes redirectAttributes) {
        etudiantService.saveOrUpdateEtudiant(etudiant);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/etudiants";
    }

    @GetMapping("/delete/{matricule}")
    public String deleteEtudiant(@PathVariable("matricule") String matricule, RedirectAttributes redirectAttributes) {
        etudiantService.deleteEtudiant(matricule);
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/etudiants";
    }

    @GetMapping("/search")
    public String searchEtudiants(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam("niveau") String niveau,
            @RequestParam("institution") String institution,
            @RequestParam("minorStatus") String minorStatus,
            Model model) {
        List<Etudiant> etudiants = etudiantService.searchEtudiantsByNameOrMatriculeAndNiveauAndInstitutionAndMinorStatus(
                searchTerm, niveau, institution, minorStatus);
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("etudiantCount", etudiants.size());
        return "etudiants :: etudiantsList";
    }

    
}
