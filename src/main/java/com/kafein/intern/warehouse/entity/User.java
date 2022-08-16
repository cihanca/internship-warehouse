package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.Role;
import com.kafein.intern.warehouse.enums.RoleBasedPermission;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private RoleBasedPermission role;

    private Boolean status;

}
