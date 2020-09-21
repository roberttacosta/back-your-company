package com.backyourcompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Table(name = "visits")
@Entity
public class Visit {
    public Visit() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;
}
