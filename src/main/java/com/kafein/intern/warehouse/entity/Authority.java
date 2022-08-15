package com.kafein.intern.warehouse.entity;


import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "authorities")
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Role role;
}
