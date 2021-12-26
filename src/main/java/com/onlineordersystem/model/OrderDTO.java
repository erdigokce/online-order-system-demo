package com.onlineordersystem.model;

import com.onlineordersystem.model.enumeration.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private UUID id;
    private String productName;
    private int quantity;
    private OrderStatus status;
    private LocalDateTime createdDate;
}
