package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.Warehouse;
import lombok.Data;

@Data
public class WarehouseDetailDTO {

    private int id;

    private Warehouse warehouse;

    private int numberOfProducts;

    private int annualIncome;

    private int numberOfEmployees;

}
