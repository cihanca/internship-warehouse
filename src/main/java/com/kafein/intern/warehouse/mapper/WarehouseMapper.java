package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.Warehouse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    @Named("warehouseToEntity")
    Warehouse warehouseToEntity(WarehouseDTO warehouseDTO);

    @Named("warehouseToDTO")
    WarehouseDTO warehouseToDTO(Warehouse warehouse);

    @IterableMapping(qualifiedByName = "warehouseToDTO")
    List<WarehouseDTO> toDTOList(List<Warehouse> entityList);
}
