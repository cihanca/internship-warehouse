package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.Position;
import com.kafein.intern.warehouse.enums.Role;
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

    @ManyToOne
    private User user;

    @ManyToOne
    private User manager;


}
