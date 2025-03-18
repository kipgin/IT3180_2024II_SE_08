package com.example.BTL_CNPM.report.controller;

import com.example.BTL_CNPM.report.model.ReportDTO;
import com.example.BTL_CNPM.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<ReportDTO> getSummaryReport() {
        return ResponseEntity.ok(reportService.generateReport());
    }
}
