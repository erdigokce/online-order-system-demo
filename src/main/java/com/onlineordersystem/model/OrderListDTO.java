package com.onlineordersystem.model;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class OrderListDTO extends SearchResultDTO {

    private List<OrderDTO> orders = Collections.emptyList();

}
