package com.backyourcompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Table(name = "cards")
@Entity
public class Card {

    public Card() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @ManyToOne(targetEntity = Activity.class)
    @JoinColumn(name = "id_activity")
    private Activity activity;

    private LocalDateTime cardCreationDate;

    @ManyToOne(targetEntity = Patient.class)
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne(targetEntity = HealthInsurance.class)
    @JoinColumn(name = "id_health_insurance")
    private HealthInsurance healthInsurance;

    @ManyToOne(targetEntity = Visit.class)
    @JoinColumn(name = "id_visit")
    private Visit visit;

    @OneToMany( targetEntity=Bill.class )
    private List<Bill> bills;

    private Integer numberOfPendencies;

    private Integer numberOfOpenPendencies;

    private Integer numberOfDocuments;

    private Integer numberOfNotReceivedDocuments;

    private Integer numberOfChecklistItem;

    private Integer numberOfDoneChecklistItem;
}
