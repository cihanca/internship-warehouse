package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class ProcessDetailFilterDTO {

    private String productCode;

    private Integer productId;

    private Integer warehouseId;

}
