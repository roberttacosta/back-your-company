package com.backyourcompany.service;

import com.backyourcompany.dto.BillRequest;
import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;

public interface BillService {
    Double totalAmount(Card card);
    Bill createBill(BillRequest billRequest);
    Bill findById(Long billId);
}
