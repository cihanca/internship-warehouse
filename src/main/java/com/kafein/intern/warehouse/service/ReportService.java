package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ReportDTO;
import com.kafein.intern.warehouse.entity.ProductDetail;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ProductDetailService productDetailService;

    private final UserDetailService userDetailService;

    private final WarehouseService warehouseService;

    public ReportService(ProductDetailService productDetailService, UserDetailService userDetailService, WarehouseService warehouseService) {
        this.productDetailService = productDetailService;
        this.userDetailService = userDetailService;
        this.warehouseService = warehouseService;
    }

    public ReportDTO fetch(int warehouseId) {
        int numOfEmployees = userDetailService.getNumberOfEmployeesAtWarehouse(warehouseId);
        int numOfProduct = 100;
        return new ReportDTO(numOfEmployees,numOfProduct);
    }

}
