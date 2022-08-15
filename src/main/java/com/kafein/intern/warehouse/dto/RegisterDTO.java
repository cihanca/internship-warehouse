package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;

@Data
public class RegisterDTO {

    private String name;

    private String username;

    private String password;

    private String email;

    private Role role;
}
