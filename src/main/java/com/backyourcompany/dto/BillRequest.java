package com.backyourcompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class BillRequest {
    @NotNull
    private Double valueBill;

    @NotBlank
    private String billType;

    @NotBlank
    private String descriptionBill;

    @NotNull
    private Long idCard;
}
