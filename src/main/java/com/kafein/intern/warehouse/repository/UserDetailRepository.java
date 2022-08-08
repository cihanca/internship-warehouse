package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository <UserDetail, Integer> {


}
