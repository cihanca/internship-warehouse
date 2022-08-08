package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employees")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private int currentEmployeeNumber;
}
