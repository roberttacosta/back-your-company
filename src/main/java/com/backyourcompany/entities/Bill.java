package com.backyourcompany.entities;

import com.backyourcompany.entities.enums.BillType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Optional;

@Data
@AllArgsConstructor
@Table(name = "bills")
@Entity
public class Bill {
    public Bill() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    private Double valueBill;

    private BillType billType;

    private String descriptionBill;

    @JsonIgnore
    @ManyToOne(targetEntity = Card.class)
    @JoinColumn(name = "id_card")
    private Card card;

    public Bill(long billId, double valueBill, BillType billType, String descriptionBill, Optional<Card> card) {
    }
}
