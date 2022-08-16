package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findByName(String productName);
    List<Product> findAllByOrderByIdAsc();
}
