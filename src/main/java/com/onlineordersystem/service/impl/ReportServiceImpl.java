package com.onlineordersystem.service.impl;

import com.onlineordersystem.domain.Report;
import com.onlineordersystem.domain.ReportRepository;
import com.onlineordersystem.model.ReportDTO;
import com.onlineordersystem.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ModelMapper modelMapper) {
        this.reportRepository = reportRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void createEndOfTheDayReport(ReportDTO reportDTO) {
        Report report = modelMapper.map(reportDTO, Report.class);
        reportRepository.save(report);
    }
}
