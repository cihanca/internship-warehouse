package com.kafein.intern.warehouse.service;

import antlr.preprocessor.PreprocessorTokenTypes;
import com.kafein.intern.warehouse.dto.ReportDTO;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.Report;
import com.kafein.intern.warehouse.repository.ReportRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Service
public class ReportService {

    private final ProductDetailService productDetailService;

    private final UserDetailService userDetailService;

    private final WarehouseService warehouseService;

    private final ReportRepository reportRepository;

    public ReportService(ProductDetailService productDetailService, UserDetailService userDetailService, WarehouseService warehouseService, ReportRepository reportRepository) {
        this.productDetailService = productDetailService;
        this.userDetailService = userDetailService;
        this.warehouseService = warehouseService;
        this.reportRepository = reportRepository;
    }

    @Scheduled(cron = "0 * * ? * *")
    public void save() {
        System.out.println("WORKINGGGGGGGG");
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        saveWarehouseInfo(out);
        saveAllWarehousesInfo(out);
    }

    public void saveWarehouseInfo(Date date) {
        for (int i = 1; i <= 3; i++) {
            Report report = new Report();
            report.setTotalProfit(productDetailService.getTotalProfitAtWarehouse(i));
            report.setNumberOfProducts(productDetailService.getStockAtWarehouse(i));
            report.setDate(date);
            report.setWarehouseId(i);
            reportRepository.save(report);
        }
    }

    public void saveAllWarehousesInfo(Date date) {
        Report report = new Report();
        report.setNumberOfProducts(productDetailService.getAllStock());
        report.setNumberOfEmployees(userDetailService.getNumberOfEmployees());
        report.setTotalProfit(productDetailService.getTotalProfit());
        report.setDate(date);
        reportRepository.save(report);
    }

    public void setBoughtCount() {
    }
}
