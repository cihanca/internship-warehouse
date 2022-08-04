package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDTO;
import com.kafein.intern.warehouse.entity.Product;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.mapper.ProductMapper;
import com.kafein.intern.warehouse.repository.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    // how to inform user about product be is adding to is previously add but then remove from the list.
    private void validateBeforeSave(String productName) {
        Product fromRepo = productRepository.findByName(productName);
        if (fromRepo != null) {
            throw new RuntimeException("Product name " + productName + " is already in use!");
        }

    }

    public ProductDTO save(ProductDTO productDTO) {
        validateBeforeSave(productDTO.getName());
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDTO(productRepository.save(product));
    }

    public ProductDTO getProduct(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null)
            throw new RuntimeException("Product with given id " + id + " doesn't exist");
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> listAllProducts() {
        return productMapper.toProductDTOList(productRepository.findAllByOrderByIdAsc());
    }
}
