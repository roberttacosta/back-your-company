package com.backyourcompany.dto;

import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.HealthInsurance;
import com.backyourcompany.entities.Patient;
import com.backyourcompany.entities.enums.SlaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CardResponse {
    private Integer daysSinceCreated;
    private SlaStatus slaStatus;
    private Patient patient;
    private HealthInsurance healthInsurance;
    private Long visitId;
    private List<Bill> bills;
    private Double totalAmount;
    private Integer numberOfPendencies;
    private Integer numberOfOpenPendencies;
    private Integer numberOfDocuments;
    private Integer numberOfNotReceivedDocuments;
    private Integer numberOfChecklistItem;
    private Integer numberOfDoneChecklistItem;
}

























