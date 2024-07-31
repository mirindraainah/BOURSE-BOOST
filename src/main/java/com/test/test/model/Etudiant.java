package com.test.test.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.time.LocalDate;

@Entity
@Table(name = "etudiant")
public class Etudiant {
    @Id
    private String matricule;
    
    private String nom;
    private String sexe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datenais;

    private String institution;
    private String niveau;
    private String mail;
    private String annee_univ;

    // getters, setters
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAnnee_univ() {
        return annee_univ;
    }

    public void setAnnee_univ(String annee_univ) {
        this.annee_univ = annee_univ;
    }
}
