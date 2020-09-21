package com.backyourcompany.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResponse {
    private List cards;
    private Integer numberPage;
    private Integer numberOfCards;
    private Integer cardsPerPage;
    private Integer currentPage;
    private Boolean firstPage;
    private Boolean lastPage;
    private Integer totalCardsOk;
    private Integer totalCardsWarning;
    private Integer totalCardsDelayed;
}
