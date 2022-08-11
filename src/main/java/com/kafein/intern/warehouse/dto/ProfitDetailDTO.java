package com.kafein.intern.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProfitDetailDTO {

    private int id;

    private int boughtCount;

    private int soldCount;

    private ZonedDateTime date;

    private int profit;

    private WarehouseNameDTO warehouse;

}
