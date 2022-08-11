package com.kafein.intern.warehouse.controller;

import com.kafein.intern.warehouse.dto.ProfitDetailFilterDTO;
import com.kafein.intern.warehouse.service.ProfitDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@EnableScheduling
@RestController
@RequestMapping("/profitDetail")
public class ProfitDetailController {

    private final ProfitDetailService profitDetailService;

    public ProfitDetailController(ProfitDetailService profitDetailService) {
        this.profitDetailService = profitDetailService;
    }

    public ResponseEntity<?> save() {
        profitDetailService.save();
        return null;
    }

    @PostMapping("/filterProfits")
    public ResponseEntity<?> filterProfits(@RequestBody ProfitDetailFilterDTO dto) {
        return new ResponseEntity<>(profitDetailService.filterProfits(dto), HttpStatus.OK);
    }

}
