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

    private int profit;

    private int expenditure;

    private int income;

    private ProcessType processType;

    private Date date;
}
