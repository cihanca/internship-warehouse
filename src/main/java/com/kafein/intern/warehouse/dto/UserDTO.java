package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Role;
import com.kafein.intern.warehouse.enums.RoleBasedPermission;
import lombok.Data;

@Data
public class UserDTO {

    private int id;

    private String name;

    private String username;

    private String password;

    private String email;

    private RoleBasedPermission role;

    private Boolean status;

}
