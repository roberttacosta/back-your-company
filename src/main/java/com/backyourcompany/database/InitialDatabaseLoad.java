package com.backyourcompany.database;

import com.backyourcompany.entities.*;
import com.backyourcompany.entities.enums.BillType;
import com.backyourcompany.repositories.*;
import com.backyourcompany.service.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public class InitialDatabaseLoad {

    @Transactional
    public void addActivity(ActivityRepository activityRepository){
        activityRepository.saveAndFlush(new Activity(1L, "Berçário", "Organizar prontuário", 5));
        activityRepository.saveAndFlush(new Activity(2L, "Centro Cirurgico", "Organizar prontuário", 5));
    }

    @Transactional
    public void addCardAndBill(CardRepository cardRepository, ActivityService activityService, BillRepository billRepository, HealthInsuranceService healthInsuranceService,
                               PatientService patientService, VisitService visitService, CardService cardService){
        Activity activity = activityService.findById(1L);
        HealthInsurance healthInsurance = healthInsuranceService.findByHealthInsuranceName("Unimed");
        Patient patient = patientService.findByPatientName("José da Silva");
        Visit visit = visitService.findById(1L);

        Card card = new Card(1L, activity, LocalDateTime.now(), patient, healthInsurance, visit, null,
                3, 2, 5, 2, 8, 7);

        cardRepository.saveAndFlush(card);

        billRepository.saveAndFlush(new Bill(1L, 200.00, BillType.AMBULATORIAL, "Emergencia", card));
        billRepository.saveAndFlush(new Bill(2L, 300.00, BillType.HOSPITALAR, "Cirurgia", card));

        List<Bill> bills = billRepository.findAll();

        Card cardDataBase = cardService.findById(1L);
        cardDataBase.setBills(bills);
        cardRepository.save(cardDataBase);
    }

    @Transactional
    public void addHealthInsurance(HealthInsuranceRepository healthInsuranceRepository){
        healthInsuranceRepository.saveAndFlush(new HealthInsurance(1L, "Unimed"));
        healthInsuranceRepository.saveAndFlush(new HealthInsurance(2L, "SulAmerica"));
    }

    @Transactional
    public void addPatient(PatientRepository patientRepository){
        patientRepository.saveAndFlush(new Patient(1L, "José da Silva"));
        patientRepository.saveAndFlush(new Patient(2L, "Leandro Almeida"));
    }

    @Transactional
    public void addVisit(VisitRepository visitRepository){
        visitRepository.saveAndFlush(new Visit(1L));
        visitRepository.saveAndFlush(new Visit(2L));
    }
}
