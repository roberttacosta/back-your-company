package com.backyourcompany.service;

import com.backyourcompany.dto.CardRequest;
import com.backyourcompany.dto.PageResponse;
import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;

public interface CardService {
    Card createCard(CardRequest cardRequest);
    Card findById(Long cardId);
    void saveNewBill(Card card, Bill bill);
    PageResponse getCard(Long activityId, String patientName, Long visitId, Long billId, String filter, Integer page, Integer size);
}
