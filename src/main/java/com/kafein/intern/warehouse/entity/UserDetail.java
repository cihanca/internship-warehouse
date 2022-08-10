package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.Department;
import com.kafein.intern.warehouse.enums.Position;
import com.kafein.intern.warehouse.enums.Role;
import com.kafein.intern.warehouse.enums.Sex;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int wage;

    @Enumerated(EnumType.STRING)
    private Position position;

    @ManyToOne
    private Warehouse warehouse;

    @OneToOne
    private User user;

    @ManyToOne
    private User manager;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Department department;

    private Boolean status;

}
