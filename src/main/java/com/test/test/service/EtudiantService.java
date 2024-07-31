package com.test.test.service;

import com.test.test.model.Etudiant;
import com.test.test.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Optional<Etudiant> getEtudiantById(String matricule) {
        return etudiantRepository.findById(matricule);
    }

    public Etudiant saveOrUpdateEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public void deleteEtudiant(String matricule) {
        etudiantRepository.deleteById(matricule);
    }

    // institutions
    public List<String> getAllInstitutions() {
        return etudiantRepository.findAll()
                .stream()
                .map(Etudiant::getInstitution)
                .distinct()
                .collect(Collectors.toList());
    }

    // recherche par nom, matricule, niveau, institution, minorité
    public List<Etudiant> searchEtudiantsByNameOrMatriculeAndNiveauAndInstitutionAndMinorStatus(
            String searchTerm, String niveau, String institution, String minorStatus) {
        return etudiantRepository.searchByNomOrMatriculeAndNiveauAndInstitutionAndMinorStatus(
                searchTerm, niveau, institution, minorStatus);
    }

    // étudiants par niveau
    public Map<String, Long> etudiantsParNiveau() {
        return etudiantRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Etudiant::getNiveau, Collectors.counting()));
    }

    // nombre total des étudiants
    public long totalEtudiants() {
        return etudiantRepository.count();
    }

}

