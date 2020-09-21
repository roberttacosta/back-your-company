package com.backyourcompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CardRequest {
    @NotBlank
    private String activityTitle;

    @NotBlank
    private String patientName;

    @NotBlank
    private String healthInsuranceName;

    @NotNull
    private Long visitId;

    private Integer numberOfPendencies;

    private Integer numberOfOpenPendencies;

    private Integer numberOfDocuments;

    private Integer numberOfNotReceivedDocuments;

    private Integer numberOfChecklistItem;

    private Integer numberOfDoneChecklistItem;
}
