package com.backyourcompany.service.impl;

import com.backyourcompany.entities.Patient;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.PatientRepository;
import com.backyourcompany.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl (PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient findByPatientName(String patientName) {
        return patientRepository.findByName(patientName)
                .orElseThrow(() -> new ObjectNotFoundException("The patient: "+patientName+" not exists."));
    }
}
