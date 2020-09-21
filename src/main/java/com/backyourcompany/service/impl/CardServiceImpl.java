package com.backyourcompany.service.impl;

import com.backyourcompany.dto.CardRequest;
import com.backyourcompany.dto.CardResponse;
import com.backyourcompany.dto.PageResponse;
import com.backyourcompany.entities.*;
import com.backyourcompany.entities.enums.SlaStatus;
import com.backyourcompany.exceptions.DataIntegrityException;
import com.backyourcompany.exceptions.ObjectNotFoundException;
import com.backyourcompany.repositories.CardRepository;
import com.backyourcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private final String PRIORITY = "PRIORITY";
    private final String TO_RECEIVE = "TO_RECEIVE";
    private final String TO_SEND = "TO_SEND";

    private final CardRepository cardRepository;
    private final ActivityService activityService;
    private final BillService billService;
    private final HealthInsuranceService healthInsuranceService;
    private final PatientService patientService;
    private final VisitService visitService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, ActivityService activityService, @Lazy BillService billService,
                           HealthInsuranceService healthInsuranceService, PatientService patientService, VisitService visitService) {
        this.cardRepository = cardRepository;
        this.activityService = activityService;
        this.billService = billService;
        this.healthInsuranceService = healthInsuranceService;
        this.patientService = patientService;
        this.visitService = visitService;
    }

    @Override
    public Card createCard(CardRequest cardRequest) {
        this.numberOfOpenPendenciesIsLessThanNumberofPendencies(cardRequest);
        this.numberOfNotReceivedDocumentsIsLessThanNumberOfDocuments(cardRequest);
        this.numberOfDoneChecklistItemIsLessThanNumberOfChecklistItem(cardRequest);

        Activity activity = activityService.findByTitleActivity(cardRequest.getActivityTitle());
        Patient patient = patientService.findByPatientName(cardRequest.getPatientName());
        HealthInsurance healthInsurance = healthInsuranceService.findByHealthInsuranceName(cardRequest.getHealthInsuranceName());
        Visit visit = visitService.findById(cardRequest.getVisitId());

        return cardRepository.saveAndFlush(this.buildNewCard(cardRequest, activity, patient, healthInsurance, visit));
    }

    @Override
    public Card findById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new ObjectNotFoundException("The card: " + cardId + " not exists."));
    }

    @Override
    public void saveNewBill(Card card, Bill bill){
        List<Bill> bills = card.getBills();
        bills.add(bill);
        card.setBills(bills);

        cardRepository.saveAndFlush(card);
    }

    @Override
    public PageResponse getCard(Long activityId, String patientName, Long visitId, Long billId, String filter, Integer page, Integer size){
        List<Card> cards = new ArrayList<>();

        this.validateActivityId(activityId, cards);
        this.validatePatientName(patientName, cards);
        this.validateVisitId(visitId, cards);
        this.validateBillId(billId, cards);
        this.validateFilter(filter, cards);

        this.ifDontHaveParameter(cards, activityId, patientName, visitId, billId, filter);

        List<CardResponse> cardResponses = this.changeCardInCardResponse(cards);

        PagedListHolder pagedListHolder = new PagedListHolder(cardResponses);
        pagedListHolder.setPageSize(size);
        pagedListHolder.setPage(page);

        return this.buildPageResponse(pagedListHolder, this.totalCardsOk(cardResponses), this.totalCardsWarning(cardResponses), this.totalCardsDelayed(cardResponses));
    }

    private PageResponse buildPageResponse (PagedListHolder pagedListHolder, Integer totalCardsOk, Integer totalCardsWarning, Integer totalCardsDelayed){
        return new PageResponse(pagedListHolder.getPageList(), pagedListHolder.getPageCount(), pagedListHolder.getNrOfElements(), pagedListHolder.getPageSize(),
                pagedListHolder.getPage(), pagedListHolder.isFirstPage(), pagedListHolder.isLastPage(), totalCardsOk, totalCardsWarning, totalCardsDelayed);
    }

    private Card buildNewCard(CardRequest cardRequest, Activity activity, Patient patient, HealthInsurance healthInsurance, Visit visit) {
        return new Card(null, activity, LocalDateTime.now(), patient, healthInsurance, visit, null, cardRequest.getNumberOfPendencies(), cardRequest.getNumberOfOpenPendencies(),
                cardRequest.getNumberOfDocuments(), cardRequest.getNumberOfNotReceivedDocuments(), cardRequest.getNumberOfChecklistItem(), cardRequest.getNumberOfDoneChecklistItem());
    }

    private void numberOfOpenPendenciesIsLessThanNumberofPendencies(CardRequest cardRequest){
        if(cardRequest.getNumberOfOpenPendencies() > cardRequest.getNumberOfPendencies())
            throw new DataIntegrityException("It is not possible that the numberOfOpenPendencies is greater than the numberOfPendencies.");
    }

    private void numberOfNotReceivedDocumentsIsLessThanNumberOfDocuments(CardRequest cardRequest){
        if(cardRequest.getNumberOfNotReceivedDocuments() > cardRequest.getNumberOfDocuments())
            throw new DataIntegrityException("It is not possible that the numberOfNotReceivedDocuments is greater than the numberOfDocuments.");
    }

    private void numberOfDoneChecklistItemIsLessThanNumberOfChecklistItem(CardRequest cardRequest){
        if(cardRequest.getNumberOfDoneChecklistItem() > cardRequest.getNumberOfChecklistItem())
            throw new DataIntegrityException("It is not possible that the numberOfDoneChecklistItem is greater than the numberOfChecklistItem.");
    }

    private CardResponse buildCardResponse(Card card){
        return new CardResponse(this.daysSinceCreated(card), this.defineSlaStatus(card), card.getPatient(), card.getHealthInsurance(), card.getVisit().getVisitId(),
                card.getBills(), billService.totalAmount(card), card.getNumberOfPendencies(), card.getNumberOfOpenPendencies(), card.getNumberOfDocuments(),
                card.getNumberOfNotReceivedDocuments(), card.getNumberOfChecklistItem(), card.getNumberOfDoneChecklistItem());
    }

    private Integer daysSinceCreated(Card card){
        return LocalDateTime.now().getDayOfMonth() - card.getCardCreationDate().getDayOfMonth();
    }

    private SlaStatus defineSlaStatus(Card card){
        Double days = (double) (LocalDateTime.now().getDayOfMonth() - card.getCardCreationDate().getDayOfMonth());
        double div = days / card.getActivity().getSla();

        if(div<=0.75) return SlaStatus.OK;
        else if (div < 1) return SlaStatus.WARNING;
        else return SlaStatus.DELAYED;
    }

    private void validateActivityId(Long activityId, List<Card> cards){
        if (activityId != null){
            activityService.findById(activityId);
            cards.addAll(cardRepository.findAllByActivity_ActivityId(activityId));
        }
    }

    private void validatePatientName(String patientName,List<Card> cards){
        if(patientName != null){
            patientService.findByPatientName(patientName);
            if(cards.isEmpty()){
                cards.addAll(cardRepository.findAllByPatient_Name(patientName));
            }
            else cards.removeIf(card -> !card.getPatient().getName().equals(patientName));
        }
    }

    private void validateVisitId(Long visitId, List<Card> cards){
        if(visitId != null){
            visitService.findById(visitId);
            if(cards.isEmpty()){
                cards.addAll(cardRepository.findAllByVisit_VisitId(visitId));
            }
            else cards.removeIf(card -> !card.getVisit().getVisitId().equals(visitId));
        }
    }

    private void validateBillId(Long billId, List<Card> cards){
        if(billId != null){
            Bill bill = billService.findById(billId);
            if(cards.isEmpty()){
                cards.add(cardRepository.findByBills(bill));
            }
            else cards.removeIf(card -> !card.getBills().contains(bill));
        }
    }

    private void validateFilter(String filter, List<Card> cards){
        if(filter != null) {
            switch (filter.toUpperCase()) {
                case PRIORITY:
                    if (cards.isEmpty()) {
                        cards.addAll(cardRepository.findAll());
                    }
                    break;
                case TO_RECEIVE:
                    if (cards.isEmpty()) {
                        for (Card card : cardRepository.findAll()) {
                            if (card.getNumberOfNotReceivedDocuments() > 0) cards.add(card);
                        }
                    } else cards.removeIf(card -> card.getNumberOfNotReceivedDocuments() == 0);
                    break;
                case TO_SEND:
                    if (cards.isEmpty()) {
                        for (Card card : cardRepository.findAll()) {
                            if (card.getNumberOfNotReceivedDocuments() == 0 && card.getNumberOfChecklistItem().equals(card.getNumberOfDoneChecklistItem())
                                    && card.getNumberOfOpenPendencies() == 0)
                                cards.add(card);
                        }
                    } else
                        cards.removeIf(card -> card.getNumberOfNotReceivedDocuments() != 0 && !card.getNumberOfChecklistItem().equals(card.getNumberOfDoneChecklistItem())
                                && card.getNumberOfOpenPendencies() != 0);
                    break;
                default:
                    throw new DataIntegrityException("The filter: " + filter + " not exists!");
            }
        }
    }

    private void ifDontHaveParameter (List<Card> cards, Long activityId, String patientName, Long visitId, Long billId, String filter){
        if(activityId == null && patientName == null && visitId == null && billId == null && filter == null){
            cards.addAll(cardRepository.findAll());
        }
    }

    public List<CardResponse> changeCardInCardResponse (List<Card> cards){
        List<CardResponse> cardResponses = new ArrayList<>();
        for(Card card : cards) {
            CardResponse cardResponse = this.buildCardResponse(card);
            cardResponses.add(cardResponse);
        }
        return cardResponses;
    }

    private Integer totalCardsOk(List<CardResponse> cardResponses){
        Integer totalCardsOk = 0;
        for (CardResponse cardResponse : cardResponses ){
            if(cardResponse.getSlaStatus() == SlaStatus.OK) ++totalCardsOk;
        }
        return totalCardsOk;
    }

    private Integer totalCardsWarning(List<CardResponse> cardResponses){
        Integer totalCardsWarning = 0;
        for (CardResponse cardResponse : cardResponses ){
            if(cardResponse.getSlaStatus() == SlaStatus.WARNING) ++totalCardsWarning;
        }
        return totalCardsWarning;
    }

    private Integer totalCardsDelayed(List<CardResponse> cardResponses){
        Integer totalCardsDelayed = 0;
        for (CardResponse cardResponse : cardResponses ){
            if(cardResponse.getSlaStatus() == SlaStatus.DELAYED) ++totalCardsDelayed;
        }
        return totalCardsDelayed;
    }

}
