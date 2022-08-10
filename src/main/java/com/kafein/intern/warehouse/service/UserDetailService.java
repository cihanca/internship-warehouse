package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.UserDetailFilterDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.UserDetail;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.UserDetailMapper;
import com.kafein.intern.warehouse.repository.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserDetailMapper userDetailMapper;

    public UserDetailService(UserDetailRepository userDetailRepository, UserDetailMapper userDetailMapper) {
        this.userDetailRepository = userDetailRepository;
        this.userDetailMapper = userDetailMapper;
    }

    public UserDetailDTO addEmployeeToWarehouse(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = userDetailMapper.toEntity(userDetailDTO);
        userDetail.setStatus(true);
        userDetailRepository.save(userDetail);
        return userDetailMapper.toDTO(userDetail);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<UserDetailDTO> filter(UserDetailFilterDTO userDetailFilterDTO) {
        log.info("Process filtering is running.");
        Page<UserDetail> page = userDetailRepository.findAll((root, query, criteriaBuilder) -> {
            query.distinct(true);
            query.orderBy(criteriaBuilder.asc(root.get("id")));
            List<Predicate> predicates = new ArrayList<>();

            if (userDetailFilterDTO.getUserId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("id"), userDetailFilterDTO.getUserId())));
            }

            if (userDetailFilterDTO.getUserName()  != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("name"), userDetailFilterDTO.getUserName())));
            }

            if (userDetailFilterDTO.getManagerId() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("id"), userDetailFilterDTO.getManagerId())));
            }

            if (userDetailFilterDTO.getManagerName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("user").get("name"), userDetailFilterDTO.getManagerName())));
            }

            if (userDetailFilterDTO.getWarehouseName() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("warehouseName"), userDetailFilterDTO.getWarehouseName())));
            }

            if (userDetailFilterDTO.getWarehouseID() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("warehouse").get("id"), userDetailFilterDTO.getWarehouseID())));
            }

            if (userDetailFilterDTO.getSex()!= null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("sex"), userDetailFilterDTO.getSex())));
            }

            if (userDetailFilterDTO.getDepartment() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("department"), userDetailFilterDTO.getDepartment())));
            }

            if (userDetailFilterDTO.getPosition() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("position"), userDetailFilterDTO.getPosition())));
            }

            if (userDetailFilterDTO.getStatus() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), userDetailFilterDTO.getStatus())));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));
        int num = userDetailRepository.countByWarehouse_Id(1);
        //log.info("number of employees at warehouse with id " + 1 + ": " + num);
        //log.info("Process filtering is successfully completed.");
        return userDetailMapper.toUserDetailDTOList(page.getContent());
    }


    /**
    findByUser_ıd().orElseThrow() ?????????????????
     */

    public boolean removeEmployeeFromWarehouse(UserDetailDTO userDetailDTO) {
        UserDetail userDetail = userDetailRepository.findByUser_Id(userDetailDTO.getUser().getId());
        if (userDetail == null) {
            throw new GenericServiceException("User not found with id: " + userDetailDTO.getUser().getId(), ErrorType.USER_NOT_FOUND);
        }
        userDetail.setStatus(false);
        userDetail.getUser().setStatus(false);
        userDetailRepository.save(userDetail);
        return true;
    }

    public int getNumberOfEmployeesAtWarehouse(int warehouseId) {
        int num = userDetailRepository.countByWarehouse_Id(warehouseId);
        log.info("number of employees at warehouse with id " + warehouseId + ": " + num);
        return num;
    }

    public int getNumberOfEmployees() {
        return userDetailRepository.findAll().size();
    }
}
