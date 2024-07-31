package com.test.test.repository;

import com.test.test.model.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends JpaRepository<Payer, Long> {
}
