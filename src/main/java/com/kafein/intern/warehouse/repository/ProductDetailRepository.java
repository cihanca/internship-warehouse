package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

}
