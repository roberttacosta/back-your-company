package com.backyourcompany.service.impl;

import com.backyourcompany.dto.BillRequest;
import com.backyourcompany.entities.Bill;
import com.backyourcompany.entities.Card;
import com.backyourcompany.entities.Visit;
import com.backyourcompany.entities.enums.BillType;
import com.backyourcompany.exceptions.DataIntegrityException;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.BillRepository;
import com.backyourcompany.service.BillService;
import com.backyourcompany.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final CardService cardService;

    @Autowired
    public BillServiceImpl(BillRepository billRepository, CardService cardService) {
        this.billRepository = billRepository;
        this.cardService = cardService;
    }

    @Override
    public Bill createBill(BillRequest billRequest){
        this.valueBillIsGreaterThanZero(billRequest);

        Card card = cardService.findById(billRequest.getIdCard());
        Bill bill = this.buildNewBill(billRequest, card);

        billRepository.saveAndFlush(bill);
        cardService.saveNewBill(card, bill);

        return bill;
    }

    @Override
    public Double totalAmount(Card card){
        return billRepository.findAllByCard(card).stream().mapToDouble(Bill::getValueBill).sum();
    }

    @Override
    public Bill findById(Long billId){
        return billRepository.findById(billId)
                .orElseThrow(() -> new ObjectNotFoundException("The bill: " + billId + " not exists."));
    }

    private Bill buildNewBill(BillRequest billRequest, Card card){
        return new Bill(null, billRequest.getValueBill(), this.billType(billRequest), billRequest.getDescriptionBill(), card);
    }

    private BillType billType(BillRequest billRequest){
        if(billRequest.getBillType().toUpperCase().equals(BillType.AMBULATORIAL.toString())){
            return BillType.AMBULATORIAL;
        }
        else if(billRequest.getBillType().toUpperCase().equals(BillType.HOSPITALAR.toString())){
            return BillType.HOSPITALAR;
        }
        else throw new DataIntegrityException("The billType: "+billRequest.getBillType()+" no exists.");
    }

    private void valueBillIsGreaterThanZero(BillRequest billRequest){
        if(billRequest.getValueBill() < 0)
            throw new DataIntegrityException("valueBill must be greater than zero.");
    }













}
