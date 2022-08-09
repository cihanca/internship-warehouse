package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProcessDetailDTO {

    private int id;

    private ZonedDateTime date;

    private User user;

    private ProductDetail productDetail;

    private int count;

    private ProcessType processType;

}
