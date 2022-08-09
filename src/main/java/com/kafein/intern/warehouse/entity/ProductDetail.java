package com.kafein.intern.warehouse.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.lang.module.FindException;

@Data
@Entity
@Table(name = "product_details")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;

    private int productLimit;

    private int productCount;

}
