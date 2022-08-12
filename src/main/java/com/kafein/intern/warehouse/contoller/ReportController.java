package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.dto.ReportFilterDTO;
import com.kafein.intern.warehouse.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@EnableScheduling
@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody ReportFilterDTO reportFilterDTO) {
        return new ResponseEntity<>(reportService.filter(reportFilterDTO), HttpStatus.OK);
    }

    public void save() {
        reportService.save();
    }



}
