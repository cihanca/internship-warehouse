package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDTO;
import com.kafein.intern.warehouse.entity.Product;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.ProductMapper;
import com.kafein.intern.warehouse.repository.ProductRepository;
import lombok.Data;
import org.springframework.dao.DataIntegrityViolationException;
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

    //todo unique kontolünden dolayı hata alması durumunda hatanın handle edilmesi
    public ProductDTO save(ProductDTO productDTO) {
        validateBeforeSave(productDTO.getName());
        productDTO.setStatus(true);
        productDTO.setNetIncome(productDTO.getExportPrice() - productDTO.getImportPrice());
        try {
            Product product = productMapper.toEntity(productDTO);
            return productMapper.toDTO(productRepository.save(product));
        }
        catch (Exception e) {
            throw new GenericServiceException("Product could not save!", ErrorType.PRODUCT_COULD_NOT_SAVE);
        }
    }

    public ProductDTO getProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with given id " + id + " doesn't exist"));
        return productMapper.toDTO(product);
    }

    public List<ProductDTO> listAllProducts() {
        return productMapper.toProductDTOList(productRepository.findAllByOrderByIdAsc());
    }
}
