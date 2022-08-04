package com.kafein.intern.warehouse.request;

import lombok.Data;

@Data
public class WarehouseCreateRequest {

    private int warehouseId;
    private int generalManagerId;
    private String region;
    private String district;
    private String address;

}
