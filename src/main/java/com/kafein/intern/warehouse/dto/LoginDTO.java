package com.kafein.intern.warehouse.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;
    private String password;

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }
}
