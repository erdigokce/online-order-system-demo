package com.onlineordersystem.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderActionResultDTO {

    private UUID orderId;
}
