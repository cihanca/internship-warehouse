package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WareHouseRepository extends JpaRepository<Warehouse, Integer> {

    //Warehouse findByGeneralManager(User user);
}
