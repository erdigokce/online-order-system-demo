package com.onlineordersystem.model;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2772569451574814585L;

    private String name;
    private String description;
    @PositiveOrZero(message = "product.error.quantity.mustBePositiveOrZero")
    private Integer quantity;
    private Integer pageIndex;
    private Integer pageSize;
}
