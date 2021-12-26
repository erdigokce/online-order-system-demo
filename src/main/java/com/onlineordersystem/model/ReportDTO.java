package com.onlineordersystem.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {

    private LocalDateTime dayOfReport;
    private Integer totalSold;
    private UUID sellerId;
}
