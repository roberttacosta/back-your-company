package com.backyourcompany.controller;

import com.backyourcompany.dto.BillRequest;
import com.backyourcompany.dto.CardRequest;
import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;
import com.backyourcompany.service.BillService;
import com.backyourcompany.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("bill")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService){
        this.billService = billService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bill createBill(@Valid @RequestBody BillRequest billRequest) {
        log.info("Creating the new bill {}", billRequest);
        return billService.createBill(billRequest);
    }
}
