package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findByName(String productName);

    List<Product> findAllByOrderByIdAsc();
}
