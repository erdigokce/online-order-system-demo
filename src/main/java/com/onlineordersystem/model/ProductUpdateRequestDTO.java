package com.onlineordersystem.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class ProductUpdateRequestDTO {

    @NotNull(message = "product.error.id.mustBePresent")
    private UUID id;
    @Size(max = 100, message = "product.error.name.tooLong")
    private String name;
    @Size(max = 512, message = "product.error.description.tooLong")
    private String description;
    @PositiveOrZero(message = "product.error.quantity.mustBePositiveOrZero")
    private Integer quantity;

    public boolean isAllNull() {
        return !StringUtils.hasText(name) && !StringUtils.hasText(description) && quantity == null;
    }
}
