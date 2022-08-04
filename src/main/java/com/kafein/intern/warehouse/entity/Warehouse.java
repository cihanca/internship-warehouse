package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "warehouse_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    private String warehouseName;

    private String region;

    private String district;

    private String address;

    @OneToOne
    @JoinColumn(name = "general_manager_id", referencedColumnName = "id")
    private User generalManager;

}
