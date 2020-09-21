package com.backyourcompany.service;

import com.backyourcompany.entities.Patient;

public interface PatientService {
    Patient findByPatientName(String patientName);
}
