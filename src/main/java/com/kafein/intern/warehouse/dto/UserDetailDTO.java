package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.kafein.intern.warehouse.enums.Position;
import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;

@Data
public class UserDetailDTO {

    private int id;

    private int wage;

    private Position position;

    private Warehouse warehouse;

    private User user;

    private User manager;

}
