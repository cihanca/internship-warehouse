package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.*;
import com.kafein.intern.warehouse.entity.*;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.enums.ProcessType;
import com.kafein.intern.warehouse.enums.Role;
import com.kafein.intern.warehouse.enums.RoleBasedPermission;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.ProcessDetailMapper;
import com.kafein.intern.warehouse.mapper.ProductDetailMapper;
import com.kafein.intern.warehouse.repository.ProcessDetailRepository;
import com.kafein.intern.warehouse.repository.ProductDetailRepository;
import com.kafein.intern.warehouse.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;

@Service
@Slf4j
public class ProductDetailService {

    private final ProductDetailMapper productDetailMapper;
    private final ProductDetailRepository productDetailRepository;

    private final ProcessDetailMapper processDetailMapper;
    private final ProcessDetailRepository processDetailRepository;
    private final UserRepository userRepository;




    public ProductDetailService(ProductDetailMapper productDetailMapper, ProductDetailRepository productDetailRepository,
                                ProcessDetailRepository processDetailRepository, UserRepository userRepository,
                                ProcessDetailMapper processDetailMapper) {
        this.productDetailMapper = productDetailMapper;
        this.productDetailRepository = productDetailRepository;
        this.processDetailRepository = processDetailRepository;
        this.userRepository = userRepository;
        this.processDetailMapper = processDetailMapper;
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
    public List<ProcessDetailDTO> filterProcess(ProcessFilterDTO processFilterDTO) {
        log.info("Process filtering is running.");
        Page<ProcessDetail> page = processDetailRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (processFilterDTO.getDate() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("date"), processFilterDTO.getDate())));
            }

            if (processFilterDTO.getProductName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("product").get("name"), processFilterDTO.getProductName() )));
            }

            if (processFilterDTO.getProductId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("product").get("id"), processFilterDTO.getProductId())));
            }

            if (processFilterDTO.getUserName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("user").get("name"), processFilterDTO.getUserName() )));
            }

            if (processFilterDTO.getUserId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("user").get("id"), processFilterDTO.getUserId())));
            }

            if (processFilterDTO.getWarehouseName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("warehouse").get("warehouseName"), processFilterDTO.getWarehouseName())));
            }

            if (processFilterDTO.getWarehouseId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productDetail").get("warehouse").get("id"), processFilterDTO.getWarehouseId())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));
        log.info("Process filtering is successfully completed.");
        return processDetailMapper.toProcessDTOList(page.getContent());
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

    public boolean updateProductAtWarehouse(ProductUpdateDTO productUpdateDTO) {
        User userOnDuty = userRepository.findById(productUpdateDTO.getUserId());
        if (userOnDuty.getRole() != RoleBasedPermission.ROLE_ADMIN) {
            throw new GenericServiceException("User with id: " + productUpdateDTO.getUserId() + " does not have permission.",
                    ErrorType.DO_NOT_HAVE_PERMISSION);
        }

        ProductDetail detail = productDetailRepository.findByProduct_IdAndWarehouse_Id(productUpdateDTO.getProductId(), productUpdateDTO.getWarehouseId());

        if (detail == null) {
            throw new GenericServiceException("Cannot find product detail record with product with id: " + productUpdateDTO.getWarehouseId() +
                    " or warehouse with id: " + productUpdateDTO.getWarehouseId(), ErrorType.WAREHOUSE_OR_PRODUCT_DOES_NOT_EXIST);
        }
        findProcessTypeAndExecute(productUpdateDTO.getProcessType(),detail,productUpdateDTO.getCount(), productUpdateDTO.getWarehouseId());
        productDetailRepository.save(detail);
        setProcessDetail(detail, productUpdateDTO.getUserId(), productUpdateDTO.getCount(), productUpdateDTO.getProcessType());
        return true;
    }

    public void findProcessTypeAndExecute(ProcessType processType, ProductDetail detail, int count, int warehouseId) {
        if (processType == ProcessType.ADD) {
            addProductToWarehouse(detail,count);
        }
        else if (processType == ProcessType.REMOVE) {
            removeProductToWarehouse(detail, count, warehouseId);
        }
        else {
            throw new GenericServiceException("Invalid process type!", ErrorType.INVALID_REQUEST);
        }
    }

    public boolean addProductToWarehouse(ProductDetail detail, int count) {
        detail.setProductCount(detail.getProductCount() + count);
        return true;
    }

    public boolean removeProductToWarehouse(ProductDetail detail, int count, int warehouseId) {
        if (detail.getProductCount() - count < 0) {
            throw new GenericServiceException("There is no enough product in warehouse " + warehouseId +
                    "\nNumber of products in stock: " + detail.getProductCount(), ErrorType.INSUFFICIENT_NUMBER_OF_PRODUCTS);
        }

        if (detail.getProductCount()- count < detail.getProductLimit()) {
            log.warn("If you continue to process, products in stock will be below at a critical level.");
        }
        detail.setProductCount(detail.getProductCount() - count);
        return true;
    }


    public boolean setProcessDetail(ProductDetail productDetail, int userId, int count, ProcessType processType) {
        log.info("Process Detail saving...");
        ProcessDetail processDetail = new ProcessDetail();
        processDetail.setProductDetail(productDetail);
        processDetail.setUser(userRepository.findById(userId));
        processDetail.setCount(count);
        processDetail.setProcessType(processType);
        calculateProfit(processDetail,count, productDetail.getProduct().getNetIncome(),processType);
        calculateIncome(processDetail,processType,count,productDetail.getProduct().getExportPrice());
        calculateExpenditure(processDetail,processType,count,productDetail.getProduct().getImportPrice());
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        processDetail.setDate(out);
        processDetailRepository.save(processDetail);
        log.info("Process Detail successfully saved.");
        return true;
    }

    public void calculateProfit(ProcessDetail processDetail, int productCount, int profit, ProcessType processType) {
        if (processType == ProcessType.REMOVE)
            processDetail.setProfit(Integer.toString(productCount * profit));
        else {
            processDetail.setProfit("-");
        }
    }

    public void calculateIncome(ProcessDetail processDetail,ProcessType processType, int productCount, int exportPrice) {
        if (processType == ProcessType.REMOVE) {
            processDetail.setIncome(Integer.toString(productCount * exportPrice));
        }
        else {
            processDetail.setIncome("-");
        }
    }

    public void calculateExpenditure(ProcessDetail processDetail,ProcessType processType, int productCount, int exportPrice) {
        if (processType == ProcessType.ADD) {
            processDetail.setExpenditure(Integer.toString(productCount * exportPrice));
        }
        else {
            processDetail.setExpenditure("-");
        }
    }

    public int getTotalProfitAtWarehouse(int warehouseId) {
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        int totalProfit = 0;
        for (ProcessDetail pD : processDetailList) {
            if (pD.getProductDetail().getWarehouse().getId() == warehouseId && !pD.getProfit().equals("-")) {
                totalProfit += Integer.parseInt(pD.getProfit());
            }
        }
        return totalProfit;
    }

    public int getAllStock()  {
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        int totalStock = 0;
        for (ProductDetail pD : productDetailList) {
            totalStock += pD.getProductCount();
        }
        return totalStock;
    }

    public int getTotalProfit() {
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        int totalProfit = 0;
        for (ProcessDetail pD : processDetailList) {
            if (pD.getProcessType() == ProcessType.REMOVE)
                totalProfit += Integer.parseInt(pD.getProfit());
        }
        return totalProfit;
    }

    public int getStockAtWarehouse(int warehouseId) {
        List<ProductDetail> processDetailList = productDetailRepository.findAll();
        int totalProduct = 0;
        for (ProductDetail pD : processDetailList) {
            if (pD.getWarehouse().getId() == warehouseId) {
                totalProduct += pD.getProductCount();
            }
        }
        return totalProduct;
    }

    public int getBoughtCountAtWarehouse(int warehouseId) {
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        int bought = 0;
        for (ProcessDetail pD :  processDetailList) {
            if (pD.getProductDetail().getWarehouse().getId() == warehouseId && pD.getProcessType() == ProcessType.ADD) {
                bought += pD.getCount();
            }
        }
        return bought;

    }

    public int getTotalBoughtCount() {
        List<ProcessDetail> processDetailList = processDetailRepository.findAllByProcessType(ProcessType.ADD);
        int totalBought = 0;
        for (ProcessDetail pD : processDetailList) {
            totalBought += pD.getCount();
        }
        return totalBought;
    }

    public int getSoldCountAtWarehouse(int warehouseId) {
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        int sold = 0;
        for (ProcessDetail pD :  processDetailList) {
            if (pD.getProductDetail().getWarehouse().getId() == warehouseId && pD.getProcessType() == ProcessType.REMOVE) {
                sold += pD.getCount();
            }
        }
        return sold;

    }

    public int getTotalSoldCount() {
        List<ProcessDetail> processDetailList = processDetailRepository.findAllByProcessType(ProcessType.REMOVE);
        int totalSold = 0;
        for (ProcessDetail pD : processDetailList) {
            totalSold += pD.getCount();
        }
        return totalSold;
    }

    public int getTotalExpenditure() {
        int totalExpenditure = 0;
        for (int i = 1; i <= 3; i++) {
            totalExpenditure += getExpenditureAtWarehouse(i);
        }
        return totalExpenditure;
    }

    public int getExpenditureAtWarehouse(int warehouseId) {
        int expenditure = 0;
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        for (ProcessDetail pD : processDetailList) {
            if (pD.getProductDetail().getWarehouse().getId() == warehouseId && !pD.getExpenditure().equals("-")) {
                expenditure += pD.getCount() * pD.getProductDetail().getProduct().getImportPrice();
            }
        }
        return expenditure;
    }

    public int getTotalIncome() {
        int totalIncome = 0;
        for (int i = 1; i <= 3; i++) {
            totalIncome += getExpenditureAtWarehouse(i);
        }
        return totalIncome;
    }

    public int getIncomeAtWarehouse(int warehouseId) {
        int income = 0;
        List<ProcessDetail> processDetailList = processDetailRepository.findAll();
        for (ProcessDetail pD : processDetailList) {
            if (pD.getProductDetail().getWarehouse().getId() == warehouseId && !pD.getIncome().equals("-")) {
                income += pD.getCount() * pD.getProductDetail().getProduct().getExportPrice();
            }
        }
        return income;
    }
}
