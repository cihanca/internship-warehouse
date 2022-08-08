package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private int productLimit;

    private int productCount;

}
