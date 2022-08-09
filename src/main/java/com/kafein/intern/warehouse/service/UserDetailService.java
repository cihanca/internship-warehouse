package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.UserDetailFilterDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.UserDetail;
import com.kafein.intern.warehouse.mapper.UserDetailMapper;
import com.kafein.intern.warehouse.repository.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        return userDetailMapper.toDTO(userDetailRepository.save(userDetailMapper.toEntity(userDetailDTO)));
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

            if (userDetailFilterDTO.getPosition() != null) {
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("position"), userDetailFilterDTO.getPosition())));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(0, 10));
        log.info("Process filtering is successfully completed.");
        return userDetailMapper.toUserDetailDTOList(page.getContent());
    }

    public boolean removeEmployeeToWarehouse(UserDetailDTO userDetailDTO) {
        UserDetail userDetail =userDetailRepository.findByUser_Id(userDetailDTO.getUser().getId());
        userDetail.getUser().setStatus(false);
        userDetailRepository.save(userDetail);
        return true;
    }
}
