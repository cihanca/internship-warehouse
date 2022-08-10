package com.kafein.intern.warehouse.contoller;

import com.kafein.intern.warehouse.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<?> fetch(@PathVariable  int id) {
        return new ResponseEntity<>(reportService.fetch(id), HttpStatus.OK);
    }

}
