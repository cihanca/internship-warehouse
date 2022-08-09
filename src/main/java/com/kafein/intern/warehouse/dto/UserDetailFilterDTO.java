package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.Position;
import lombok.Data;

@Data
public class UserDetailFilterDTO {

    private Integer userId;

    private String userName;

    private Integer managerId;

    private String managerName;

    private Position position;
}
