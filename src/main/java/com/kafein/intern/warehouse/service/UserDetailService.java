package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.WarehouseNameDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.UserDetail;
import com.kafein.intern.warehouse.enums.Job;
import com.kafein.intern.warehouse.mapper.UserDetailMapper;
import com.kafein.intern.warehouse.repository.UserDetailRepository;
import com.kafein.intern.warehouse.repository.UserRepository;
import com.kafein.intern.warehouse.repository.WarehouseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetail")
public class UserDetailService {
    
    private final UserDetailMapper userDetailMapper;
    
    private final UserDetailRepository userDetailRepository;

    private final UserRepository userRepository;

    private final WarehouseRepository warehouseRepository;

    public UserDetailService(WarehouseRepository warehouseRepository, UserRepository userRepository, UserDetailMapper userDetailMapper, UserDetailRepository userDetailRepository) {
        this.userDetailMapper = userDetailMapper;
        this.userDetailRepository = userDetailRepository;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public Boolean hireUser(int warehouseId, int userId, Job job, int salary) {
        if(userDetailRepository.findByWarehouse_IdAndJob(warehouseId, job) != null) {
            if(userDetailRepository.findByWarehouse_IdAndUser_Id(warehouseId, userId) == null) {
                userDetailRepository.findByWarehouse_IdAndUser_Id(warehouseId, userId).setUser(userRepository.findById(userId));
                userDetailRepository.findByWarehouse_IdAndUser_Id(warehouseId, userId).setSalary(salary);
                userDetailRepository.save(userDetailRepository.findByWarehouse_IdAndUser_Id(warehouseId, userId));
                return true;
            } else {
                return false;
            }
        }
        UserDetail userDetail = new UserDetail();
        userDetail.setUser(userRepository.findById(userId));
        userDetail.setWarehouse(warehouseRepository.findById(warehouseId));
        userDetail.setJob(job);
        userDetail.setSalary(salary);
        userDetailRepository.save(userDetail);
        return true;
    }

    public Boolean fireUser(int warehouseId, int userId) {
        UserDetail userDetail = userDetailRepository.findByWarehouse_IdAndUser_Id(warehouseId, userId);
        userDetail.setUser(null);
        userDetail.setSalary(0);
        return true;
    }

}
