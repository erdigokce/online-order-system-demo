package com.onlineordersystem.model;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ProductSearchResultDTO extends SearchResultDTO {

    private List<ProductDTO> products = Collections.emptyList();
}
