package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Department;
import com.kafein.intern.warehouse.enums.Position;
import com.kafein.intern.warehouse.enums.Sex;
import lombok.Data;

@Data
public class UserDetailFilterDTO {

    private Integer userId;

    private String userName;

    private Integer managerId;

    private String managerName;

    private String warehouseName;

    private Integer warehouseID;

    private Sex sex;

    private Department department;

    private Position position;

    private Boolean status;

}
