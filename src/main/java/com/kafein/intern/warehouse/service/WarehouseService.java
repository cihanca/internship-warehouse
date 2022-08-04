package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.kafein.intern.warehouse.mapper.WarehouseMapper;
import com.kafein.intern.warehouse.repository.WareHouseRepository;
import com.kafein.intern.warehouse.request.WarehouseCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService  {

    private final WarehouseMapper warehouseMapper;
    private final WareHouseRepository wareHouseRepository;


    public WarehouseService(WarehouseMapper warehouseMapper, WareHouseRepository wareHouseRepository) {
        this.warehouseMapper = warehouseMapper;
        this.wareHouseRepository = wareHouseRepository;
    }

    public WarehouseDTO save(WarehouseCreateRequest warehouseCreateRequest) {
        Warehouse warehouse = warehouseMapper.toEntityFromCreateDTO(warehouseCreateRequest);
        warehouse = wareHouseRepository.save(warehouse);
        return warehouseMapper.toWareHouseDTO(warehouse);
    }
}
