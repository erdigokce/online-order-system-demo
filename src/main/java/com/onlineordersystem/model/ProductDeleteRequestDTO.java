package com.onlineordersystem.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDeleteRequestDTO {

    @NotNull(message = "product.error.id.mustBePresent")
    private UUID id;
}
