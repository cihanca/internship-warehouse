package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.dto.UserNameDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.UserMapper;
import com.kafein.intern.warehouse.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GenericServiceException("This user is not found with id: " + id));
        UserDTO dto = userMapper.toDTO(user);
        return dto;
    }

    private void validateBeforeSave(String email) {
        User fromFB = userRepository.findByEmail(email);

        if (fromFB != null) {
            throw new RuntimeException("This email already in use!");
        }
    }

    public UserDTO save(UserDTO userDTO) {
        validateBeforeSave(userDTO.getEmail());
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }
}
