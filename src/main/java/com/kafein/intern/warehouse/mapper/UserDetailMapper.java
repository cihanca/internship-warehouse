package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.entity.UserDetail;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDetailMapper {

    @Named("toDTO")
    UserDetailDTO toDTO(UserDetail userDetail);

    @Named("toEntity")
    UserDetail toEntity(UserDetailDTO userDetailDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    List<UserDetailDTO> toUserDetailDTOList(List<UserDetail> userDetailList);
}
