package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Job;
import lombok.Data;

@Data
public class UserDetailDTO {

    private int id;

    private WarehouseNameDTO warehouse;

    private UserNameDTO user;

    private Job job;

    private String experience;

}
