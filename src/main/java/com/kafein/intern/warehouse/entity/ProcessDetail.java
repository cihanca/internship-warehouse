package com.kafein.intern.warehouse.entity;

import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "process_details")
public class ProcessDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private ZonedDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /*@Value("${server.address}")
    private String userServerAddress;*/

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductDetail productDetail;

    private int count;

    @Enumerated(EnumType.STRING)
    private ProcessType processType;

}
