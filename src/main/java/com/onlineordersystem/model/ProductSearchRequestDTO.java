package com.onlineordersystem.model;

import java.io.Serial;
import java.io.Serializable;
import javax.validation.constraints.Positive;
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
    @PositiveOrZero(message = "common.error.pageIndex.mustBePositiveOrZero")
    private Integer pageIndex;
    @Positive(message = "common.error.pageSize.mustBePositive")
    private Integer pageSize;
}
