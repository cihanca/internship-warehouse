package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.ReportDTO;
import com.kafein.intern.warehouse.entity.Report;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    @Named("toDTO")
    ReportDTO toDTO(Report report);

    @Named("toEntity")
    Report ToEntity(ReportDTO reportDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<ReportDTO> toDTOList(List<Report> reportList);
}
