package com.backyourcompany.repositories;

import com.backyourcompany.entities.HealthInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Long> {
    Optional<HealthInsurance> findByName(String healthInsuranceName);
}
