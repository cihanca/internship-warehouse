package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.kafein.intern.warehouse.mapper.UserMapper;
import com.kafein.intern.warehouse.mapper.WarehouseMapper;
import com.kafein.intern.warehouse.repository.UserRepository;
import com.kafein.intern.warehouse.repository.WareHouseRepository;
import com.kafein.intern.warehouse.request.WarehouseCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService  {

    private final WarehouseMapper warehouseMapper;
    private final WareHouseRepository wareHouseRepository;
    private final UserService userService;


    public WarehouseService(WarehouseMapper warehouseMapper, WareHouseRepository wareHouseRepository, UserService userService) {
        this.warehouseMapper = warehouseMapper;
        this.wareHouseRepository = wareHouseRepository;
        this.userService = userService;
    }

    public WarehouseDTO save(WarehouseCreateRequest warehouseCreateRequest) {
        Warehouse warehouse = warehouseMapper.toWarehouse(warehouseCreateRequest);
        wareHouseRepository.save(warehouse);
        return warehouseMapper.toWareHouseDTO(warehouse);
    }
}
