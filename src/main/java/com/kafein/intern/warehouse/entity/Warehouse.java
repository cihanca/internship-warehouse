package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String warehouseName;

    private String region;

    private String district;

    private String address;

    @OneToOne
    @JoinColumn(name = "general_manager_id", referencedColumnName = "id")
    private User generalManager;


}
