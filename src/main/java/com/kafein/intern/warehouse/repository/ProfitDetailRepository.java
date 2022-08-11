package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.ProfitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitDetailRepository extends JpaRepository<ProfitDetail, Integer>, JpaSpecificationExecutor {

}
