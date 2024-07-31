package com.test.test.controller;

import com.test.test.service.MailService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EmailController {

    @Autowired
    private MailService emailService;

    @GetMapping("/sendEmail/{email}")
    public String sendEmail(@PathVariable("email") String email) {
    
        String subject = "Notification pour retardataire";

        LocalDate aujourdhuiDate = LocalDate.now();
        // trois semaines
        LocalDate futurDate = aujourdhuiDate.plusWeeks(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String text = "Bonjour, vu votre retard pour la réception de la bourse, nous avons défini une date pour le prochain paiement, c'est-à-dire dans trois semaine, à partir du " + futurDate.format(formatter);
        emailService.sendSimpleMessage(email, subject, text);
        return "redirect:/retardataires?emailSent=true"; 
    }
}
