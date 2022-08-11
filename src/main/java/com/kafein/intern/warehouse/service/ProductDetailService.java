package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProcessDetailDTO;
import com.kafein.intern.warehouse.dto.ProcessDetailFilterDTO;
import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.Product;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.ProcessDetailMapper;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.ProcessDetailRepository;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import com.kafein.intern.warehouse.repository.UserRepository;
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

import static com.kafein.intern.warehouse.enums.ProcessType.ADD_PRODUCT;
import static com.kafein.intern.warehouse.enums.ProcessType.DELETE_PRODUCT;

@RestController
@RequestMapping("/productDetail")
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProcessDetailMapper processDetailMapper;
    private final ProductDetailRepository productDetailRepository;
    private final ProcessDetailRepository processDetailRepository;

    public ProductDetailService(ProductDetailMapper productDetailMapper, ProcessDetailMapper processDetailMapper, ProductDetailRepository productDetailRepository, ProcessDetailRepository processDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.processDetailMapper = processDetailMapper;
        this.productDetailRepository = productDetailRepository;
        this.processDetailRepository = processDetailRepository;
    }

    public int getSoldCount(int productDetailId) {
        int num = 0;
        List<ProcessDetail> processDetail = processDetailRepository.findByProcessTypeAndProductDetailId(DELETE_PRODUCT, productDetailId);
        for(ProcessDetail p : processDetail)
            num += p.getCount();
        return num;
    }

    public int getBoughtCount(int productDetailId) {
        int num = 0;
        List<ProcessDetail> processDetail = processDetailRepository.findByProcessTypeAndProductDetailId(ADD_PRODUCT, productDetailId);
        for(ProcessDetail p : processDetail)
            num += p.getCount();
        return num;
    }

    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail.setProductCount(productDetail.getProductCount()+1);
        return productDetailMapper.toDTO(productDetailRepository.save(productDetail));
    }

    public void saveProcess(ProductDetail pDetail, ProcessType pType, int count){
        ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
        ProcessDetail processDetail = new ProcessDetail();
        processDetail.setProductDetail(pDetail);
        processDetail.setProcessType(pType);
        processDetail.setDate(zonedDateTimeNow);
        processDetail.setCount(count);

        if(pDetail != null)
            processDetail.setUser(pDetail.getWarehouse().getGeneralManager());
        else
            processDetail.setUser(null);

        processDetailRepository.save(processDetail);
    }

    public ProductDetailDTO findById(int id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() -> new GenericServiceException("Product with id " + id + " not found!", ErrorType.PRODUCT_NOT_FOUND));
        return productDetailMapper.toDTO(productDetail);
    }

    public boolean addProductToWarehouse(int warehouseId, int productId, int count) {
        ProductDetail productDetail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId);
        productDetail.setProductCount(productDetail.getProductCount() + count);
        productDetailRepository.save(productDetail);
        saveProcess(productDetail, ADD_PRODUCT, count);
        return true;
    }

    public boolean removeProductFromWarehouse(int warehouseId, int productId, int count) {
        ProductDetail detail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId);

        if(detail.getProductCount() - count < 0)
            throw new GenericServiceException("There are not enough products with id " + productId + "!", ErrorType.NOT_ENOUGH_PRODUCTS);
        else {
            detail.setProductCount(detail.getProductCount() - count);
            productDetailRepository.save(detail);
        }

        saveProcess(detail, DELETE_PRODUCT, count);

        if(detail.getProductCount() < detail.getProductLimit())
            System.out.println("Count of products with id " + productId + " is critically low!");

        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ProductDetailDTO> filterProducts(ProductDetailFilterDTO filterDTO) {
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

        saveProcess(null, ProcessType.FILTER_PRODUCTS, 1);
        return productDetailMapper.toProductDTOList(page.getContent());
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<ProcessDetailDTO> filterProcesses(ProcessDetailFilterDTO filterDTO) {
        Page<ProcessDetail> page = processDetailRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (filterDTO.getProductId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("product").get("id"), filterDTO.getProductId())));
            }

            if (filterDTO.getProductCode() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("product").get("code"), filterDTO.getProductCode())));
            }

            if (filterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("warehouse").get("id"), filterDTO.getWarehouseId())));
            }

            if (filterDTO.getProcessType() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("processType"), filterDTO.getProcessType())));
            }

            if (filterDTO.getUserName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("name"), filterDTO.getUserName())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        saveProcess(null, ProcessType.FILTER_PROCESSES, 1);
        return processDetailMapper.toProcessDTOList(page.getContent());
    }

}
