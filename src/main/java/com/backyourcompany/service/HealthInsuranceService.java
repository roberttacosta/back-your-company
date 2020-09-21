package com.backyourcompany.service;

import com.backyourcompany.entities.HealthInsurance;

public interface HealthInsuranceService {
    HealthInsurance findByHealthInsuranceName(String healthInsuranceName);
}
