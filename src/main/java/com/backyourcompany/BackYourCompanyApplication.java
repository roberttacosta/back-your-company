package com.backyourcompany;

import com.backyourcompany.database.InitialDatabaseLoad;
import com.backyourcompany.entities.*;
import com.backyourcompany.repositories.*;
import com.backyourcompany.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BackYourCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackYourCompanyApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(ActivityRepository activityRepository, BillRepository billRepository, CardRepository cardRepository,
									  HealthInsuranceRepository healthInsuranceRepository, PatientRepository patientRepository, VisitRepository visitRepository,
									  CardService cardService, ActivityService activityService, HealthInsuranceService healthInsuranceService,
									  PatientService patientService, VisitService visitService) {
		return args -> {
			InitialDatabaseLoad initialDatabaseLoad = new InitialDatabaseLoad();

			List<Activity> activities = activityRepository.findAll();
			if(activities.isEmpty()){
				initialDatabaseLoad.addActivity(activityRepository);
			}

			List<HealthInsurance> healthInsurances = healthInsuranceRepository.findAll();
			if(healthInsurances.isEmpty()){
				initialDatabaseLoad.addHealthInsurance(healthInsuranceRepository);
			}

			List<Patient> patients = patientRepository.findAll();
			if(patients.isEmpty()){
				initialDatabaseLoad.addPatient(patientRepository);
			}

			List<Visit> visits = visitRepository.findAll();
			if(visits.isEmpty()){
				initialDatabaseLoad.addVisit(visitRepository);
			}

			List<Card> cards = cardRepository.findAll();
			List<Bill> bills = billRepository.findAll();
			if(cards.isEmpty() && bills.isEmpty()){
				initialDatabaseLoad.addCardAndBill(cardRepository, activityService, billRepository, healthInsuranceService, patientService, visitService, cardService);
			}

		};
	}

}
