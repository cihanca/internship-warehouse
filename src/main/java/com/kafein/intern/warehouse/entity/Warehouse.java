package com.kafein.intern.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Data
public class Warehouse {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int warehouseId;

    private String region;
    private String district;

    @Lob
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "general_manager_id", referencedColumnName = "generalManager")
    private User generalManager;

    public Warehouse() {}
}
