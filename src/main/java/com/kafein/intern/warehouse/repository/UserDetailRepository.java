package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.UserDetail;
import com.kafein.intern.warehouse.enums.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Integer>, JpaSpecificationExecutor {

    UserDetail findByWarehouse_IdAndUser_Id(int warehouseId, int userId);

    Boolean findByWarehouse_IdAndJob(int warehouseId, Job job);
}
