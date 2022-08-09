package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class ProductDetailFilterDTO {

    private Integer warehouseId;

    private String warehouseRegion;

    private String warehouseDistrict;

    private Integer productId;

    private String productName;

    private String productCode;

}
