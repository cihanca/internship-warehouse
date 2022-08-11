package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.dto.WarehouseNameDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "profit_details")
public class ProfitDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int boughtCount;

    private int soldCount;

    private ZonedDateTime date;

    private int profit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Warehouse warehouse;

}
