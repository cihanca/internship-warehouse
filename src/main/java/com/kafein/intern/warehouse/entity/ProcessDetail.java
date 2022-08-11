package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table
@Entity(name = "processDetails")
public class ProcessDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductDetail productDetail;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private int count;

    private String profit;

    private String expenditure;

    private String income;

    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    private Date date;
}
