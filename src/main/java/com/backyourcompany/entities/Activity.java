package com.backyourcompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Table(name = "activities")
@Entity
public class Activity {

    public Activity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @NotNull
    private String activityTitle;

    @NotNull
    private String activitySubtitle;

    @NotNull
    private Integer sla;
}
