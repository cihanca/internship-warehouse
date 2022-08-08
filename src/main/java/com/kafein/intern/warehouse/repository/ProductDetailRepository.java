package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.sun.istack.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer>, JpaSpecificationExecutor {

    ProductDetail findByProduct_IdAndWarehouse_Region(int product_id, String warehouse_region);

    int countByProduct_KeyAndWarehouse_WarehouseName(String product_key, String warehouse_warehouseName);

    ProductDetail findByProduct_Key(String product_key);

    Page<ProductDetail> findAll(@Nullable Specification productSpecification, Pageable pageable);

    ProductDetail findByProduct_Id(int product_id);

    ProductDetail findByProduct_IdAndWarehouse_Id(int product_id, int warehouse_id);
}
