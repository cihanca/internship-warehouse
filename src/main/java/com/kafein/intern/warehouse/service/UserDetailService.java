package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.entity.UserDetail;
import com.kafein.intern.warehouse.mapper.UserDetailMapper;
import com.kafein.intern.warehouse.repository.UserDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final UserDetailMapper userDetailMapper;

    public UserDetailService(UserDetailRepository userDetailRepository, UserDetailMapper userDetailMapper) {
        this.userDetailRepository = userDetailRepository;
        this.userDetailMapper = userDetailMapper;
    }

    public UserDetailDTO save(UserDetailDTO userDetailDTO) {
        return userDetailMapper.toDTO(userDetailRepository.save(userDetailMapper.toEntity(userDetailDTO)));
    }

    public UserDetailDTO get(int id) {
        return userDetailMapper.toDTO(userDetailRepository.findById(id).orElse(null));
    }
}
