package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.WarehouseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseDetailRepository extends JpaRepository<WarehouseDetail,Integer> {

}
