package com.test.test.repository;


import com.test.test.model.Montant;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MontantRepository extends JpaRepository<Montant, Long> {
  Optional<Montant> findByNiveau(String niveau);
}

