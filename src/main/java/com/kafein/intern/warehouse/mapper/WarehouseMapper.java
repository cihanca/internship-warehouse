package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    @Named("warehouseToEntity")
    Warehouse warehouseToEntity(WarehouseDTO warehouseDTO);

    @Named("warehouseToDTO")
    WarehouseDTO warehouseToDTO(Warehouse warehouse);
}
