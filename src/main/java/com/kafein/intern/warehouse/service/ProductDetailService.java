package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDTO;
import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.WarehouseNameDTO;
import com.kafein.intern.warehouse.entity.Product;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.mapper.ProductMapper;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;

    public ProductDetailService(ProductDetailMapper productDetailMapper, ProductDetailRepository productDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository = productDetailRepository;
    }

    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail.setProductCount(productDetail.getProductCount()+1);
        return productDetailMapper.toDTO(productDetailRepository.save(productDetail));
    }
}
