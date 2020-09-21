package com.backyourcompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Table(name = "health_insurance")
@Entity
public class HealthInsurance {
    public HealthInsurance() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthInsuranceId;

    @NotNull
    private String name;
}
