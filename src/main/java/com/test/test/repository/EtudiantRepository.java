package com.test.test.repository;

import com.test.test.model.Etudiant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, String> {
  @Query("SELECT e FROM Etudiant e WHERE " +
  "(UPPER(e.nom) LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
  "UPPER(e.matricule) LIKE UPPER(CONCAT('%', :searchTerm, '%'))) AND " +
  "(:niveau = 'tous' OR e.niveau = :niveau) AND " +
  "(:institution = 'tous' OR e.institution = :institution) AND " +
  "(:minorStatus = 'tous' OR " +
  "(:minorStatus = 'mineur' AND TIMESTAMPDIFF(YEAR, e.datenais, CURRENT_DATE) < 18) OR " +
  "(:minorStatus = 'majeur' AND TIMESTAMPDIFF(YEAR, e.datenais, CURRENT_DATE) >= 18))")
List<Etudiant> searchByNomOrMatriculeAndNiveauAndInstitutionAndMinorStatus(
   @Param("searchTerm") String searchTerm,
   @Param("niveau") String niveau,
   @Param("institution") String institution,
   @Param("minorStatus") String minorStatus);
}

