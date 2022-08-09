package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.ProductDetailDTO;
import com.kafein.intern.warehouse.dto.ProductDetailFilterDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import com.kafein.intern.warehouse.entity.ProductDetail;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.enums.Role;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.ProcessDetailRepository;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import com.kafein.intern.warehouse.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;

@Service
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;

    private final ProcessDetailRepository processDetailRepository;
    private final UserRepository userRepository;


    public ProductDetailService(ProductDetailMapper productDetailMapper, ProductDetailRepository productDetailRepository,
                                ProcessDetailRepository processDetailRepository, UserRepository userRepository) {
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository = productDetailRepository;
        this.processDetailRepository = processDetailRepository;
        this.userRepository = userRepository;
    }

    public ProductDetailDTO save(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailMapper.toEntity(productDetailDTO);
        productDetail.setProductCount(productDetail.getProductCount()+1);
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

            if (filterDTO.getWarehouseDistrict() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("warehouseDistrict"), filterDTO.getWarehouseDistrict())));
            }

            if (filterDTO.getWarehouseRegion() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("warehouseRegion"), filterDTO.getWarehouseRegion())));
            }

            if (filterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("warehouseId"), filterDTO.getWarehouseId())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));

        return productDetailMapper.toProductDTOList(page.getContent());

    }


    public boolean removeProductFromWarehouse(ProcessType processType, int warehouseId, int productId, int count, int userId) {
        User userOnDuty = userRepository.findById(userId).orElseThrow(() ->
                new GenericServiceException("This user is not found with id: " + userId, ErrorType.USER_NOT_FOUND));
        if (userOnDuty.getRole() != Role.ADMIN) {
            throw new GenericServiceException("User with id: " + userId + " does not have permission.",
                    ErrorType.DO_NOT_HAVE_PERMISSION);
        }
        if (productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId) == null) {
            throw new GenericServiceException("Cannot find product detail record with product with id: " + productId +
                    " or warehouse with id: " + warehouseId, ErrorType.WAREHOUSE_OR_PRODUCT_DOES_NOT_EXIST);
        }
        ProductDetail detail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId);
        if (processType == ProcessType.ADD) {
            detail.setProductCount(detail.getProductCount() + count);
        }
        if (processType == ProcessType.REMOVE) {
            if (detail.getProductCount() - count < 0) {
                throw new GenericServiceException("There is no enough product in warehouse " + warehouseId +
                        "\nNumber of products in stock: " + detail.getProductCount(), ErrorType.INSUFFICIENT_NUMBER_OF_PRODUCTS);
            }
            detail.setProductCount(detail.getProductCount() - count);
        }
        productDetailRepository.save(detail);
        setProcessDetail(detail, userId, count, processType);
        return true;
    }

    public boolean setProcessDetail(ProductDetail productDetail, int userId, int count, ProcessType processType) {
        ProcessDetail processDetail = new ProcessDetail();
        processDetail.setProductDetail(productDetail);
        processDetail.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId)));
        processDetail.setCount(count);
        processDetail.setProcessType(processType);
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        processDetail.setDate(out);
        processDetailRepository.save(processDetail);
        return true;
    }
}
