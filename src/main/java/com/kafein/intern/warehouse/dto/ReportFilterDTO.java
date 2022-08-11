package com.kafein.intern.warehouse.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ReportFilterDTO {

    private Integer warehouseId;

    private Boolean isAll;

    private Date startDate;

    private Date endDate;
}
