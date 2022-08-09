package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.Job;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Warehouse warehouse;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Enumerated(EnumType.STRING)
    private Job job;

    private int salary;

}
