package com.onlineordersystem.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductSearchResultDTO {

    private List<ProductDTO> productDTOS;
}
