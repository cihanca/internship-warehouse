package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.UserMapper;
import com.kafein.intern.warehouse.mapper.WarehouseMapper;
import com.kafein.intern.warehouse.repository.UserRepository;
import com.kafein.intern.warehouse.repository.WarehouseRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseMapper warehouseMapper;

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseMapper warehouseMapper, WarehouseRepository warehouseRepository) {
        this.warehouseMapper = warehouseMapper;
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseDTO save(WarehouseDTO warehouseDTO) {
        validateWarehouse(warehouseDTO.getWarehouseName());
        Warehouse warehouse = warehouseMapper.warehouseToEntity(warehouseDTO);
        warehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.warehouseToDTO(warehouse);
    }

    private void validateWarehouse(String warehouseName) {
        Warehouse warehouse = warehouseRepository.findByWarehouseName(warehouseName);

        if (warehouse != null) {
            throw new GenericServiceException("This warehouse already exists with this warehouse name: " + warehouseName, ErrorType.WAREHOUSE_NAME_ALREADY_IN_USE);
        }
    }

    public WarehouseDTO getWarehouse(int id) {
        Warehouse warehouse = warehouseRepository.findById(id);

        if (warehouse == null) {
            throw new RuntimeException("Warehouse with id " + id + " not found!");
        }
        //.orElseThrow(() -> new GenericServiceException("User with id: " + id + "not found!"));
        return warehouseMapper.warehouseToDTO(warehouse);
    }

    public List<WarehouseDTO> listWarehouses() {
        List<Warehouse> warehouseList = warehouseRepository.findAllByOrderByIdAsc();
        return warehouseMapper.toDTOList(warehouseList);
    }
}
