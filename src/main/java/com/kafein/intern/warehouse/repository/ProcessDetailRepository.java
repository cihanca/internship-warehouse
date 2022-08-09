package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProcessDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Integer>, JpaSpecificationExecutor {
}
