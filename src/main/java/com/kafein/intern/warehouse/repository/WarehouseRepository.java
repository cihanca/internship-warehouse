package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Warehouse save(Warehouse warehouse);

    Warehouse findByWarehouseName(String warehouseName);

    Warehouse findByWarehouseId(int id);

    List<Warehouse> findAllByOrderByWarehouseIdAsc();

}
