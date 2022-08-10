package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.sun.istack.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Integer> {

    ProcessDetail findByProductDetailProduct_IdAndProductDetailWarehouse_Id(int productId, int warehouseId);

    ProcessDetail findByProductDetail_Id(int id);

    ProcessDetail findByProductDetail_Product_IdAndProductDetail_Warehouse_Id(int productDetail_product_id, int productDetail_warehouse_id);

    List<ProcessDetail> findAllByProcessType(ProcessType processType);
    Page<ProcessDetail> findAll(@Nullable Specification productSpecification, Pageable pageable);
}
