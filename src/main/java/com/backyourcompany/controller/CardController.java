package com.backyourcompany.controller;

import com.backyourcompany.dto.CardRequest;
import com.backyourcompany.dto.PageResponse;
import com.backyourcompany.entities.Card;
import com.backyourcompany.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Card createCard(@Valid @RequestBody CardRequest cardRequest) {
        log.info("Creating the new card {}", cardRequest);
        return cardService.createCard(cardRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageResponse getAllActivities(@RequestParam(required = false) Long activityId,
                                         @RequestParam(required = false) String patientName,
                                         @RequestParam(required = false) Long visitId,
                                         @RequestParam(required = false) Long billId,
                                         @RequestParam(required = false) String filter,
                                         @RequestParam(value= "page", defaultValue = "0") Integer page,
                                         @RequestParam(value= "size", defaultValue = "1") Integer size) {
        log.info("Show all activities");
        return cardService.getCard(activityId, patientName, visitId, billId, filter, page, size);
    }
}
