package com.onlineordersystem.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SearchResultDTO {

    private long totalElements = 0;
    private int totalPages = 1;
    private int currentPage = 0;
    private int pageSize = 10;
}
