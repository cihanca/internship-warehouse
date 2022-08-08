package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Warehouse findByWarehouseName(String warehouseName);

    Warehouse findById(int id);

    List<Warehouse> findAllByOrderByIdAsc();

}
