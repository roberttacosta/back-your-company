package com.backyourcompany.service.impl;

import com.backyourcompany.entities.HealthInsurance;
import com.backyourcompany.entities.Visit;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.VisitRepository;
import com.backyourcompany.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }

    @Override
    public Visit findById(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new ObjectNotFoundException("The visit: " + visitId + " not exists."));
    }
}
