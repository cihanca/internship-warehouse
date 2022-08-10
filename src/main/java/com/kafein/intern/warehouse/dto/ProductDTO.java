package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private int id;

    private String name;

    private String code;

    private Boolean status;

    private int importPrice;

    private int exportPrice;

    private int netIncome;
}
