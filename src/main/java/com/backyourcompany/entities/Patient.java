package com.backyourcompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Table(name = "patients")
@Entity
public class Patient {
    public Patient() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    @NotNull
    private String name;

}
