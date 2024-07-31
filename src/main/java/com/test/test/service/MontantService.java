package com.test.test.service;

import com.test.test.model.Montant;
import com.test.test.repository.MontantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MontantService {

    @Autowired
    private MontantRepository montantRepository;

    public List<Montant> getAllMontants() {
        return montantRepository.findAll();
    }

    public Optional<Montant> getMontantById(Long id) {
        return montantRepository.findById(id);
    }

    public Montant saveOrUpdateMontant(Montant montant) {
        return montantRepository.save(montant);
    }

    public void deleteMontant(Long id) {
        montantRepository.deleteById(id);
    }

    public Montant getMontantByNiveau(String niveau) {
        Optional<Montant> montant = montantRepository.findByNiveau(niveau);
        return montant.orElse(null);
    }
}
