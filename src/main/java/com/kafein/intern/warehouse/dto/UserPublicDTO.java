package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;

@Data
public class UserPublicDTO {

    private int id;

    private String name;

    private String username;

    private String email;

    private Role role;
}
