package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.WarehouseDetailDTO;
import com.kafein.intern.warehouse.entity.WarehouseDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WarehouseDetailMapper {

    @Named("toEntity")
    WarehouseDetail toEntity(WarehouseDetailDTO warehouseDetailDTO);

    @Named("toDTO")
    WarehouseDetailDTO toDTO(WarehouseDetail warehouseDetail);

    @IterableMapping(qualifiedByName = "toDTO")
    List<WarehouseDetailDTO> toDTOList(List<WarehouseDetail> warehouseDetailList);
}
