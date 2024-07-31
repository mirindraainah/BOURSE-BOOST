package com.test.test.model;

import jakarta.persistence.*;

@Entity
@Table(name = "montant")
public class Montant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idniv;
    
    private String niveau;
    private int montant;
    private int equipement;

    // getters, setters
    public Long getIdniv() {
        return idniv;
    }

    public void setIdniv(Long idniv) {
        this.idniv = idniv;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getEquipement() {
        return equipement;
    }

    public void setEquipement(int equipement) {
        this.equipement = equipement;
    }
}
