package com.backyourcompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ActivityRequest {
    @NotBlank
    private String activityTitle;

    @NotBlank
    private String activitySubtitle;

    @NotNull
    private Integer sla;
}
