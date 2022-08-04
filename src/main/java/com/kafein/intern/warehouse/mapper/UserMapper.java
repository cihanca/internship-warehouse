package com.kafein.intern.warehouse.mapper;

import com.kafein.intern.warehouse.dto.UserNameDTO;
import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.entity.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toUserNameDTO")
    UserNameDTO toUserNameDTO(User user);

    @IterableMapping(qualifiedByName = "toUserNameDTO")
    List<UserNameDTO> toUserNameDTOList(List<User> userList);

    @Named("toEntity")
    User toEntity(UserDTO userDTO);

    @Named("toDTO")
    @Mapping(target = "password", ignore = true)
    UserDTO toDTO(User user);

    @IterableMapping(qualifiedByName = "toDTO")
    List<UserDTO> toDTO(List<User> userList);

}
