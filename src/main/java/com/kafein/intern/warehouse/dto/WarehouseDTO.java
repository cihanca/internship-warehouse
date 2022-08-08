package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class WarehouseDTO {

    private int id;

    private String warehouseName;

    private String region;

    private String district;

    private String address;

    private UserNameDTO generalManager;

}
