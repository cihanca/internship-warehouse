package com.kafein.intern.warehouse.request;

import com.kafein.intern.warehouse.dto.UserNameDTO;
import lombok.Data;

@Data
public class WarehouseCreateRequest {

    private int warehouseId;
    private UserNameDTO generalManager;
    private String region;
    private String district;
    private String address;

}
