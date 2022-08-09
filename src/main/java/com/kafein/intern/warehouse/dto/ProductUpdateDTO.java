package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;

@Data
public class ProductUpdateDTO {

    ProcessType processType;
    int warehouseId;
    int productId;
    int count;
    int userId;

}
