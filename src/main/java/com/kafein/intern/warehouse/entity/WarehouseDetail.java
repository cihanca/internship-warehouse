package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "warehouse_detail")
public class WarehouseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private Warehouse warehouse;

    private int numberOfProducts;

    private int annualIncome;

    private int numberOfEmployees;

}
