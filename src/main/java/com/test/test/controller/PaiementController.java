package com.test.test.controller;

import com.test.test.model.Montant;
import com.test.test.model.Payer;
import com.test.test.model.Etudiant;
import com.test.test.service.EtudiantService;
import com.test.test.service.PayerService;
import com.test.test.service.MontantService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/paiements")
public class PaiementController {

    @Autowired
    private PayerService payerService;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private MontantService montantService;

    @GetMapping
    public String getAllPaiements(Model model) {
        List<Payer> paiements = payerService.getAllPaye();
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        List<Etudiant> retardataires = payerService.getRetardataires();

        model.addAttribute("paiements", paiements);
        model.addAttribute("etudiants", etudiants);
        model.addAttribute("retardataires", retardataires);
        model.addAttribute("payer", new Payer());
        model.addAttribute("payeCount", paiements.size());

        return "paiements";
    }

    @PostMapping("/add")
    public String addPaiement(@ModelAttribute("payer") Payer payer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Payer> paiements = payerService.getAllPaye();
            List<Etudiant> etudiants = etudiantService.getAllEtudiants();
            model.addAttribute("paiements", paiements);
            model.addAttribute("etudiants", etudiants);
            return "paiements";
        }
    
        payer.setDate(LocalDateTime.now());
        payerService.saveOrUpdatePayer(payer);
        redirectAttributes.addFlashAttribute("added", true);
    
        return "redirect:/paiements";
    }
    
    @PostMapping("/update")
    public String updatePaiement(@ModelAttribute Payer payer, Model model, RedirectAttributes redirectAttributes) {  
        List<Payer> paiements = payerService.getAllPaye();
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        model.addAttribute("paiements", paiements);
        model.addAttribute("etudiants", etudiants);
        payerService.saveOrUpdatePayer(payer);
        redirectAttributes.addFlashAttribute("updated", true);
        
        return "redirect:/paiements";
    }

    @GetMapping("/delete/{idpaye}")
    public String deletePaiement(@PathVariable("idpaye") Long idpaye, RedirectAttributes redirectAttributes) {
        payerService.deletePayer(idpaye);
        redirectAttributes.addFlashAttribute("deleted", true);  
        return "redirect:/paiements";
    }

    
    // génération de reçu PDF
    @GetMapping("/recu/{idpaye}")
    public ResponseEntity<byte[]> generateReceiptPdf(@PathVariable("idpaye") Long idpaye) {
        Optional<Payer> payerOptional = payerService.getPayerById(idpaye);
        if (!payerOptional.isPresent()) {
            return ResponseEntity
                    .status(404)
                    .body(null);
        }

        Payer payer = payerOptional.get();
        Etudiant etudiant = payer.getEtudiant();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        try {
            Document document = new Document(PageSize.A4);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();

            // en-tête
            document.add(new Paragraph("Aujourd’hui le " + formatter.format(LocalDateTime.now())) {{
                setAlignment(Element.ALIGN_CENTER);
            }});            
            document.add(new Paragraph("Date de paiement : " + formatter.format(payer.getDate())));
            document.add(new Paragraph("Matricule : " + etudiant.getMatricule()));
            document.add(new Paragraph(etudiant.getNom()));
            document.add(new Paragraph("Né(e) le " + formatter.format(etudiant.getDatenais())));
            document.add(new Paragraph(etudiant.getSexe()));
            document.add(new Paragraph("Institution : " + etudiant.getInstitution() + " / Niveau : " + etudiant.getNiveau()));
            document.add(new Paragraph(" ")); // ligne vide

            // montant pour le niveau
            Montant montantNiveau = montantService.getMontantByNiveau(etudiant.getNiveau());
            if (montantNiveau == null) {
                document.add(new Paragraph("Montant non disponible pour le niveau: " + etudiant.getNiveau()));
            } else {
                int montantParMois = montantNiveau.getMontant();
                int totalMontant = payer.getNbr_mois() * montantParMois;
                int equipement = payer.isEquipement() ? montantNiveau.getEquipement() : 0;
                int total = totalMontant + equipement;
                
                // tableau paiement
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.addCell("Mois");
                table.addCell("Montant");

                table.addCell("Equipement");
                table.addCell(String.valueOf(equipement));

                int moisDepart = payer.getMois_depart();
                for (int i = 0; i < payer.getNbr_mois(); i++) {
                    int mois = (moisDepart + i) % 12;
                    String moisNom = getMoisNom(mois == 0 ? 12 : mois);
                    table.addCell(moisNom);
                    table.addCell(String.valueOf(montantParMois));
                }
                
                table.addCell("Total");
                table.addCell(String.valueOf(total));

                document.add(table);

                document.add(new Paragraph(" "));
                document.add(new Paragraph("Total Payé : " + total + " Ariary"));
            }

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "reçu.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(out.toByteArray());
        } catch (DocumentException ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

    private String getMoisNom(int mois) {
        String[] moisNoms = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        return moisNoms[mois - 1];
    }
}