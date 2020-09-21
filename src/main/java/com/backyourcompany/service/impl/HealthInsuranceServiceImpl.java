package com.backyourcompany.service.impl;

import com.backyourcompany.entities.HealthInsurance;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.HealthInsuranceRepository;
import com.backyourcompany.service.HealthInsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthInsuranceServiceImpl implements HealthInsuranceService {
    private final HealthInsuranceRepository healthInsuranceRepository;

    @Autowired
    public HealthInsuranceServiceImpl (HealthInsuranceRepository healthInsuranceRepository){
        this.healthInsuranceRepository = healthInsuranceRepository;
    }

    @Override
    public HealthInsurance findByHealthInsuranceName(String healthInsuranceName) {
        return healthInsuranceRepository.findByName(healthInsuranceName)
                .orElseThrow(() -> new ObjectNotFoundException("The health insurance: " + healthInsuranceName + " not exists."));
    }
}