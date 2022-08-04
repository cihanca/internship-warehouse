package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.WarehouseDTO;
import com.kafein.intern.warehouse.entity.Warehouse;
import com.kafein.intern.warehouse.request.WarehouseCreateRequest;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Named("toWareHouseDTO")
    WarehouseDTO toWareHouseDTO(Warehouse warehouse);

    @IterableMapping(qualifiedByName = "toWareHouseDTO")
    List<WarehouseDTO> toWareHouseDTOList(List<Warehouse> warehouseList);

    @Named("toEntity")
    Warehouse toEntity(WarehouseDTO warehouseDTO);

}
