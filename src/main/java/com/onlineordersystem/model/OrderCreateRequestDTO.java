package com.onlineordersystem.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequestDTO {

    @NotNull(message = "product.error.id.mustBePresent")
    private UUID productId;
    @NotNull
    @PositiveOrZero(message = "product.error.quantity.mustBePositiveOrZero")
    private Integer quantity;

}
