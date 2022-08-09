package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.mapper.UserMapper;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        User user = userRepository.findByIdAndStatus(id,true);

        if (user == null) {
            throw new GenericServiceException("User with id " + id + " not found!", ErrorType.USER_NOT_FOUND);
        }
        //.orElseThrow(() -> new GenericServiceException("User with id: " + id + "not found!"));
        return userMapper.toDTO(user);
    }

    private void validateBeforeSave(String email) {
        User fromFB = userRepository.findByEmailAndStatus(email, true);

        if (fromFB != null) {
            throw new GenericServiceException("This email already in use!", ErrorType.EMAIL_ALREADY_IN_USE);
        }
    }

    public UserDTO save(UserDTO userDTO) {
        validateBeforeSave(userDTO.getEmail());
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        User user = userMapper.toEntity(getUser(id));
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public boolean deleteUser(int id) {
        User user = userMapper.toEntity(getUser(id));
        user.setStatus(false);
        userRepository.save(user);
        return true;
    }

    public List<UserDTO> listUsers() {
        //List<User> userList = userRepository.findByRoleOrderByIdAsc(ADMIN);
        //List<User> userList = userRepository.findByRoleOrderByIdDesc(USER);
        List<User> userList = userRepository.findByStatusOrderByIdAsc(true);
        List<UserDTO> userDTOList = new ArrayList<>();
        return userMapper.toDTO(userList);
    }

}
