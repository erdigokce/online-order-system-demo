package com.onlineordersystem.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateRequestDTO {

    @NotBlank(message = "product.error.name.notPresent")
    @Size(max = 100, message = "product.error.name.tooLong")
    private String name;
    @Size(max = 512, message = "product.error.description.tooLong")
    private String description;
    @PositiveOrZero(message = "product.error.quantity.mustBePresent")
    private int quantity;
}
