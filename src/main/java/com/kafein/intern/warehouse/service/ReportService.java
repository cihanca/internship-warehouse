package com.kafein.intern.warehouse.service;

import antlr.preprocessor.PreprocessorTokenTypes;
import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.dto.ReportDTO;
import com.kafein.intern.warehouse.dto.ReportFilterDTO;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.Report;
import com.kafein.intern.warehouse.mapper.ReportMapper;
import com.kafein.intern.warehouse.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class ReportService {

    private final ProductDetailService productDetailService;

    private final UserDetailService userDetailService;

    private final WarehouseService warehouseService;

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    public ReportService(ProductDetailService productDetailService, UserDetailService userDetailService,
                         WarehouseService warehouseService, ReportRepository reportRepository, ReportMapper reportMapper) {
        this.productDetailService = productDetailService;
        this.userDetailService = userDetailService;
        this.warehouseService = warehouseService;
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
    }

    @Scheduled(cron = "0 * * ? * *")
    public void save() {
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        log.info("Inventory updated. Last save is made at: " + out);
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
            report.setIncome(productDetailService.getIncomeAtWarehouse(i));
            report.setExpenditure(productDetailService.getExpenditureAtWarehouse(i));
            report.setNumberOfBought(productDetailService.getBoughtCountAtWarehouse(i));
            report.setNumberOfSold(productDetailService.getSoldCountAtWarehouse(i));
            reportRepository.save(report);
        }
    }

    public void saveAllWarehousesInfo(Date date) {
        Report report = new Report();
        report.setNumberOfProducts(productDetailService.getAllStock());
        report.setTotalProfit(productDetailService.getTotalProfit());
        report.setWarehouseId(0);
        report.setIncome(productDetailService.getTotalIncome());
        report.setExpenditure(productDetailService.getTotalExpenditure());
        report.setNumberOfBought(productDetailService.getTotalBoughtCount());
        report.setNumberOfSold(productDetailService.getTotalSoldCount());
        report.setDate(date);
        reportRepository.save(report);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ReportDTO> filter(ReportFilterDTO filterDTO) {

        Page<Report> page = reportRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouseId"), filterDTO.getWarehouseId())));
            }

            if (filterDTO.getIsAll() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouseId"), 0)));
            }

            if (filterDTO.getStartDate() != null && filterDTO.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("date"), filterDTO.getStartDate(),filterDTO.getEndDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        return reportMapper.toDTOList(page.getContent());
    }


}
