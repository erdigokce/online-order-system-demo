package com.onlineordersystem.service;

import com.onlineordersystem.model.ReportDTO;

public interface ReportService {

    void createEndOfTheDayReport(ReportDTO reportDTO);
}
