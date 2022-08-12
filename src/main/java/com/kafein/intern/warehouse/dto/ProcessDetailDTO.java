package com.kafein.intern.warehouse.dto;

import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ProcessType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class ProcessDetailDTO {

    private int id;

    private ProductDetailDTO productDetail;

    private UserNameDTO user;

    private int count;

    private String profit;

    private String expenditure;

    private String income;

    private ProcessType processType;

    private Date date;
}
