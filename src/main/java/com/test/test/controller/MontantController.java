package com.test.test.controller;

import com.test.test.model.Montant;
import com.test.test.service.MontantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/montants")
public class MontantController {

    @Autowired
    private MontantService montantService;

    @GetMapping
    public String getAllMontants(Model model) {
        List<Montant> montants = montantService.getAllMontants();
        model.addAttribute("montants", montants);
        model.addAttribute("montant", new Montant());
        model.addAttribute("niveauCount", montants.size());
        return "montants";
    }

    @PostMapping("/add")
    public String addMontant(@ModelAttribute Montant montant, RedirectAttributes redirectAttributes) {
        montantService.saveOrUpdateMontant(montant);
        redirectAttributes.addFlashAttribute("added", true);
        return "redirect:/montants";
    }

    @PostMapping("/update")
    public String updateMontant(@ModelAttribute Montant montant, RedirectAttributes redirectAttributes) {
        montantService.saveOrUpdateMontant(montant);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/montants";
    }

    @GetMapping("/delete/{id}")
   public String deleteMontant(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        montantService.deleteMontant(id);
        redirectAttributes.addFlashAttribute("deleted", true);
        return "redirect:/montants";
    }
}



// package com.test.test.controller;

// import com.test.test.model.Montant;
// import com.test.test.repository.MontantRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @Controller
// @RequestMapping("/montants")
// public class MontantController {

//     @Autowired
//     private MontantRepository montantRepository;

//     @GetMapping
//     public String getAllMontants(Model model) {
//         List<Montant> montants = montantRepository.findAll();
//         model.addAttribute("montants", montants);
//         return "montants"; // Le nom du fichier HTML où les montants sont affichés
//     }

//     @PostMapping("/add")
//     public String addMontant(@ModelAttribute Montant montant) {
//         montantRepository.save(montant);
//         return "redirect:/montants";
//     }

//     @PostMapping("/update/{id}")
//     public String updateMontant(@PathVariable("id") Long id, @ModelAttribute Montant montant) {
//         montant.setIdniv(id);
//         montantRepository.save(montant);
//         return "redirect:/montants";
//     }

//     @GetMapping("/delete/{id}")
//     public String deleteMontant(@PathVariable("id") Long id) {
//         montantRepository.deleteById(id);
//         return "redirect:/montants";
//     }
// }
