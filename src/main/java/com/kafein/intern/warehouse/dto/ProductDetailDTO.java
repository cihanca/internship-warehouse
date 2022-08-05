package com.kafein.intern.warehouse.dto;
import lombok.Data;

@Data
public class ProductDetailDTO {

    private int id;

    private WarehouseNameDTO warehouse;

    private ProductDTO product;

    private int productLimit;

    private int productCount;
}
