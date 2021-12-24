package com.onlineordersystem.model;

import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequestDTO {

    private String name;
    private String description;
    @PositiveOrZero(message = "product.error.quantity.mustBePositiveOrZero")
    private Integer quantity;
}
