package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.enums.ProcessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Integer>, JpaSpecificationExecutor {

    List<ProcessDetail> findByProcessTypeAndProductDetailId(ProcessType process_type, int product_detail_id);
}
