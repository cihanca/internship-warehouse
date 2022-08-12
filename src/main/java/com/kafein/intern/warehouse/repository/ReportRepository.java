package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.Report;
import com.sun.istack.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository <Report, Integer> {
    Page<Report> findAll(@Nullable Specification reportSpecification, Pageable pageable);
    List<Report> getByDateBetween(Date start, Date end);
}
