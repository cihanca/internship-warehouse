package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.User;
import lombok.Data;

@Data
public class WarehouseDTO {

    private int warehouseId;
    private String region;
    private String district;
    private String address;
    private User generalManager;
}
