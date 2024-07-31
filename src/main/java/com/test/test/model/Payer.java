package com.test.test.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "payer")
public class Payer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpaye;

    @ManyToOne
    @JoinColumn(name = "matricule", nullable = false)
    private Etudiant etudiant;

    private String annee_univ;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;

    private int nbr_mois;
    
    private int mois_depart;

    private boolean equipement; 

    // getters, setters
    public Long getIdpaye() {
        return idpaye;
    }

    public void setIdpaye(Long idpaye) {
        this.idpaye = idpaye;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public String getAnnee_univ() {
        return annee_univ;
    }

    public void setAnnee_univ(String annee_univ) {
        this.annee_univ = annee_univ;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getNbr_mois() {
        return nbr_mois;
    }

    public void setNbr_mois(int nbr_mois) {
        this.nbr_mois = nbr_mois;
    }

    public int getMois_depart() {
        return mois_depart;
    }

    public void setMois_depart(int mois_depart) {
        this.mois_depart = mois_depart;
    }

    public boolean isEquipement() {
        return equipement;
    }

    public void setEquipement(boolean equipement) {
        this.equipement = equipement;
    }
}
