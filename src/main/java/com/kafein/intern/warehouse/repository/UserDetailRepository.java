package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.UserDetail;
import com.sun.istack.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail,Integer> {

    UserDetail findByUser_Id(int user_id);
    Page<UserDetail> findAll(@Nullable Specification productSpecification, Pageable pageable);
    int countByWarehouse_Id(int warehouse_id);

    Boolean findByWarehouse_Id(int warehouseId);
}
