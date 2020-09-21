package com.backyourcompany.service;

import com.backyourcompany.entities.Visit;

public interface VisitService {
    Visit findById(Long visitId);
}
