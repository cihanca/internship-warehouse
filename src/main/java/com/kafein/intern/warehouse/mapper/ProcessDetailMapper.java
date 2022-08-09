package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.ProcessDetailDTO;
import com.kafein.intern.warehouse.entity.ProcessDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessDetailMapper {

    @Named("toDTO")
    ProcessDetailDTO toDTO(ProcessDetail processDetail);

    @Named("toEntity")
    ProcessDetail toEntity(ProcessDetailDTO processDetailDTO);

    List<ProcessDetailDTO> toProcessDTOList(List<ProcessDetail> processDetailList);
}
