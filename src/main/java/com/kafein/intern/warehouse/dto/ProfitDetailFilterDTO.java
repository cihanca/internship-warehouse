package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class ProfitDetailFilterDTO {

    private Integer warehouseId;

    private String warehouseRegion;

    private String warehouseDistrict;

    private String warehouseName;

    private Integer date;

    private Integer profit;

}
