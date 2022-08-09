package com.kafein.intern.warehouse.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessFilterDTO {

    Date date;

    String productName;

    Integer productId;

    Integer userId;

    String userName;

    Integer warehouseId;

    String warehouseName;



}
