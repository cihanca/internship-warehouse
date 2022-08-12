package com.kafein.intern.warehouse.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;

    private int numberOfProducts;

    private int totalProfit;

    private int warehouseId;

    private int numberOfBought;

    private int numberOfSold;

    private int expenditure;

    private int income;


}
