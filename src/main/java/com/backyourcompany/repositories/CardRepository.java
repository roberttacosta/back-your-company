package com.backyourcompany.repositories;

import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByActivity_ActivityId(Long activityId);
    List<Card> findAllByPatient_Name (String patientName);
    List<Card> findAllByVisit_VisitId(Long visitId);
    Card findByBills(Bill bill);
}
