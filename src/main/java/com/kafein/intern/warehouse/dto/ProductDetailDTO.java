package com.kafein.intern.warehouse.dto;
import com.kafein.intern.warehouse.entity.Product;
import lombok.Data;

@Data
public class ProductDetailDTO {

    private int id;

    private WarehouseNameDTO warehouse;

    private Product product;

    private int productLimit;

    private int productCount;
}
