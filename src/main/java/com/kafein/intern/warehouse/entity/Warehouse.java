package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    private String region;
    private String district;

    @Lob
    private String address;

    @OneToOne
    @JoinColumn(name = "general_manager_id", referencedColumnName = "id")
    private User generalManager;

}
