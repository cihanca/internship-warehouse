package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository <Report, Integer> {

}
