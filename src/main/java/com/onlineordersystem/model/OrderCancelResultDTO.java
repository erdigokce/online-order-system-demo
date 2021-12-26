package com.onlineordersystem.model;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCancelResultDTO {

    private UUID canceledOrderId;
}
