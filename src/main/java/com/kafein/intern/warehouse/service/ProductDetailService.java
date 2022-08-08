package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;


@Service
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;

    public ProductDetailService(ProductDetailMapper productDetailMapper, ProductDetailRepository productDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository = productDetailRepository;
    }

    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetailRepository.save(productDetail);
        System.out.println("id: " + productDetail.getProduct().getId());
        ProductDetail newDetail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productDetail.getProduct().getId(),productDetail.getWarehouse().getId());
        if (newDetail != null) {
            System.out.println(newDetail.getProduct().getName());
            System.out.println(newDetail.getProduct() == null);
        }
        String key = newDetail.getProduct().getKey();
        String name = newDetail.getWarehouse().getWarehouseName();
        updateProductCount(key, name);
        System.out.println("warehouse name: " + newDetail.getWarehouse().getWarehouseName());
        return productDetailMapper.toDTO(productDetail);
    }


    void updateProductCount(String key, String warehouseName) {
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        int productCount = productDetailRepository.countByProduct_KeyAndWarehouse_WarehouseName(key,
                warehouseName);
        for (ProductDetail p: productDetailList) {
            System.out.println("key for each: " + p.getProduct().getKey());
            System.out.println("key for each: " + p.getProduct().getName());
            if ((p.getProduct().getKey()).equals(key)) {
                p.setProductCount(productCount);
            }
        }
    }

    public ProductDetailDTO findById(int id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() -> new RuntimeException("Product detail not found with id: " + id));
        return productDetailMapper.toDTO(productDetail);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ProductDetailDTO> filter(ProductDetailFilterDTO filterDTO) {

        Page<ProductDetail> page = productDetailRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.getProductId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("product").get("id"), filterDTO.getProductId())));
            }

            if (filterDTO.getProductCode() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("product").get("code"), filterDTO.getProductCode())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        return productDetailMapper.toProductDTOList(page.getContent());

    }


    public boolean removeProductFromWarehouse(int warehouseId, int productId, int count) {

        ProductDetail detail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId);
        detail.setProductCount(detail.getProductCount() - count);
        productDetailRepository.save(detail);
        return true;
    }
}
