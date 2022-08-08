package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findByName(String productName);
    int countByName(String name);
    List<Product> findAllByOrderByIdAsc();
}
