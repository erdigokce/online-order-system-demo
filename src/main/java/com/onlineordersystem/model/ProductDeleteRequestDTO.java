package com.onlineordersystem.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDeleteRequestDTO {

    @NotNull(message = "product.error.id.mustBePresent")
    private UUID id;
}
