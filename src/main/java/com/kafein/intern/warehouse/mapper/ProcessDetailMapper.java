package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.ProcessDetailDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessDetailMapper {

    @Named("toDTO")
    ProcessDetailDTO toDTO(ProcessDetail processDetail);

    @Named("toEntity")
    ProcessDetail toEntity(ProcessDetailDTO processDetailDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ProcessDetailDTO> toProcessDTOList(List<ProcessDetail> processDetailList);
}
