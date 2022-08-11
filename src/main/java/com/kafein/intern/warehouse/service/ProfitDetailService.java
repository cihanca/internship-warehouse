package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProfitDetailDTO;
import com.kafein.intern.warehouse.dto.ProfitDetailFilterDTO;
import com.kafein.intern.warehouse.entity.*;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/profitDetail")
public class ProfitDetailService {
    private final ProductDetailService productDetailService;

    private final ProductDetailRepository productDetailRepository;

    private final ProfitDetailRepository profitDetailRepository;

    private final WarehouseRepository warehouseRepository;

    private final ProductDetailMapper productDetailMapper;

    private static int soldCount, boughtCount, totalProfit;

    public ProfitDetailService(ProductDetailMapper productDetailMapper, WarehouseRepository warehouseRepository, ProfitDetailRepository profitDetailRepository, ProductDetailRepository productDetailRepository, ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
        this.productDetailRepository = productDetailRepository;
        this.profitDetailRepository = profitDetailRepository;
        this.warehouseRepository = warehouseRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Scheduled(cron = "0 * * ? * *")
    public void save() {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        List<Warehouse> wList = warehouseRepository.findAll();
        for(Warehouse w : wList) {
            List<ProductDetail> pDetail = productDetailRepository.findByWarehouseId(w.getId());
            for(ProductDetail p : pDetail){
                int id = p.getId();
                int buyPPU = productDetailService.findById(id).getProduct().getPriceBuy();
                int sellPPU = productDetailService.findById(id).getProduct().getPriceSell();
                soldCount += productDetailService.getSoldCount(id);
                boughtCount += productDetailService.getBoughtCount(id);
                totalProfit += (productDetailService.getSoldCount(id) * sellPPU) - (productDetailService.getBoughtCount(id) * buyPPU);
            }
            ProfitDetail profitDetail = new ProfitDetail();
            profitDetail.setProfit(totalProfit);
            profitDetail.setDate(zonedDateTimeNow);
            profitDetail.setBoughtCount(boughtCount);
            profitDetail.setSoldCount(soldCount);
            profitDetail.setWarehouse(w);
            profitDetailRepository.save(profitDetail);
            totalProfit = 0;
            boughtCount = 0;
            soldCount = 0;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ProfitDetailDTO> filterProfits(ProfitDetailFilterDTO filterDTO) {
        Page<ProfitDetail> page = profitDetailRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("id"), filterDTO.getWarehouseId())));
            }

            if (filterDTO.getDate() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("date"), filterDTO.getDate())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        productDetailService.saveProcess(null, ProcessType.FILTER_PROFITS, 1);
        return productDetailMapper.toProfitDTOList(page.getContent());
    }

}
