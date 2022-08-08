package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessDetailRepository extends JpaRepository<ProcessDetail, Integer> {

    ProcessDetail findByProductDetailProduct_IdAndProductDetailWarehouse_Id(int productId, int warehouseId);

    ProcessDetail findByProductDetail_Id(int id);

    ProcessDetail findByProductDetail_Product_IdAndProductDetail_Warehouse_Id(int productDetail_product_id, int productDetail_warehouse_id);
}
