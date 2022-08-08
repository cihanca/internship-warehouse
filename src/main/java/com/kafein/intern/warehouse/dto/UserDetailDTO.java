package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.Warehouse;
import lombok.Data;

@Data
public class UserDetailDTO {

    private int id;

    private WarehouseNameDTO warehouse;

    private UserNameDTO user;

    private int currentEmployeeNumber;
}
