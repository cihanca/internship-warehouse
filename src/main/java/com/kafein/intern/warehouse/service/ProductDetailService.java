package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.ProcessDetailRepository;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;
    private final ProcessDetailRepository processDetailRepository;

    public ProductDetailService(ProductDetailMapper productDetailMapper, ProductDetailRepository productDetailRepository, ProcessDetailRepository processDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository = productDetailRepository;
        this.processDetailRepository = processDetailRepository;
    }

    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));

        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail.setProductCount(productDetail.getProductCount()+1);

        ProcessDetail processDetail = new ProcessDetail();
        processDetail.setProductDetail(productDetail);
        processDetail.setProcessType(ProcessType.ADD_PRODUCT);
        processDetail.setDate(zonedDateTimeNow);
        processDetail.setCount(1);
        processDetail.setUser(productDetail.getWarehouse().getGeneralManager());
        processDetailRepository.save(processDetail);

        return productDetailMapper.toDTO(productDetailRepository.save(productDetail));
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

            if (filterDTO.getProductName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("product").get("name"), filterDTO.getProductName())));
            }

            if (filterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("id"), filterDTO.getWarehouseId())));
            }

            if (filterDTO.getWarehouseRegion() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("region"), filterDTO.getWarehouseRegion())));
            }

            if (filterDTO.getWarehouseDistrict() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("district"), filterDTO.getWarehouseDistrict())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        return productDetailMapper.toProductDTOList(page.getContent());

    }

    public boolean removeProductFromWarehouse(int warehouseId, int productId, int count) {
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        ProductDetail detail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId);

        if(detail.getProductCount() - count < 0)
            throw new GenericServiceException("There are not enough products with id " + productId + "!", ErrorType.NOT_ENOUGH_PRODUCTS);
        else {
            detail.setProductCount(detail.getProductCount() - count);
            productDetailRepository.save(detail);
        }

        ProcessDetail processDetail = new ProcessDetail();
        processDetail.setProductDetail(detail);
        processDetail.setProcessType(ProcessType.DELETE_PRODUCT);
        processDetail.setDate(zonedDateTimeNow);
        processDetail.setCount(count);
        processDetail.setUser(detail.getWarehouse().getGeneralManager());
        processDetailRepository.save(processDetail);

        if(detail.getProductCount() < detail.getProductLimit())
            System.out.println("Count of products with id " + productId + " is critically low!");

        return true;

    }
}
