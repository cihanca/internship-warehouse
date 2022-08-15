package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.dto.UserDTO;
import com.kafein.intern.warehouse.dto.UserDetailDTO;
import com.kafein.intern.warehouse.dto.UserNameDTO;
import com.kafein.intern.warehouse.dto.UserPublicDTO;
import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.mapper.UserMapper;
import com.kafein.intern.warehouse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserDTO getUser(int id) {
        User user = userRepository.findById(id);
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

    /**
     * mappers for user whose id is kept secret.
     *
     * @return list of users which are converted DTO in the database. each user has all properties but password.
     */
    public List<UserPublicDTO> listUsers() {
        return userMapper.toUserPublicListDTO(userRepository.findAll());
    }

    /**
     * deletes user from repo.
     *
     * @param id, takes user id to check repo
     * @return list of users in the database. If passed id exist, it removes user whose id is matched.
     */
    public List<UserPublicDTO> editUserList(int id) {
        userRepository.deleteById(id);
        return userMapper.toUserPublicListDTO(userRepository.findAll());
    }

   public UserPublicDTO updateUserPassword(int id, String newPassword) {
       User user = userRepository.findById(id);
       user.setPassword(newPassword);
       userRepository.save(user);
       return userMapper.toUserPublicDTO(user);
   }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }


}
