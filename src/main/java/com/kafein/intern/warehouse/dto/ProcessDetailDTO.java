package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProcessDetailDTO {

    private int id;

    private ZonedDateTime date;

    private UserNameDTO user;

    private ProductDetailDTO productDetail;

    private int count;

    private ProcessType processType;

}
