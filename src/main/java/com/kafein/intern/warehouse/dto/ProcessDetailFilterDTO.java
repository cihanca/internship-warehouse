package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;

@Data
public class ProcessDetailFilterDTO {

    private String productCode;

    private Integer productId;

    private Integer warehouseId;

    private ProcessType processType;

    private String userName;

}
