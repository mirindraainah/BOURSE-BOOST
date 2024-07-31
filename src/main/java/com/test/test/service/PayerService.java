package com.test.test.service;

import com.test.test.model.Etudiant;
import com.test.test.model.Payer;
import com.test.test.repository.PayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PayerService {

    @Autowired
    private PayerRepository payerRepository;

    @Autowired
    private EtudiantService etudiantService;

    public List<Payer> getAllPaye() {
        return payerRepository.findAll();
    }

    public Optional<Payer> getPayerById(Long idpaye) {
        return payerRepository.findById(idpaye);
    }

    public Payer saveOrUpdatePayer(Payer payer) {
        if (payer.getDate() == null) {
            payer.setDate(LocalDateTime.now());
        }
        return payerRepository.save(payer);
    }

    public void deletePayer(Long idpaye) {
        payerRepository.deleteById(idpaye);
    }

    // retardataires
    public List<Etudiant> getRetardataires() {
        List<Payer> paiements = getAllPaye();
        List<String> matriculesPaye = paiements.stream()
                .map(payer -> payer.getEtudiant().getMatricule())
                .collect(Collectors.toList());

        return etudiantService.getAllEtudiants().stream()
                .filter(etudiant -> !matriculesPaye.contains(etudiant.getMatricule()))
                .collect(Collectors.toList());
    }

    public List<Etudiant> getRetardatairesForMonth(Month mois) {
        List<Payer> paiements = getAllPaye();
        List<String> matriculesPaye = paiements.stream()
                .filter(payer -> payer.getDate().getMonth().equals(mois))
                .map(payer -> payer.getEtudiant().getMatricule())
                .collect(Collectors.toList());

        return etudiantService.getAllEtudiants().stream()
                .filter(etudiant -> !matriculesPaye.contains(etudiant.getMatricule()))
                .collect(Collectors.toList());
    }

    // total paiements
    public long totalPaye() {
        return payerRepository.count();
    }
    
}
