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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new GenericServiceException("User not found int the database", ErrorType.USER_NOT_FOUND);
        }
        else {
            log.info("User found in the database with username: "  + username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserDTO getUser(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new GenericServiceException("This user is not found with id: " + id, ErrorType.USER_NOT_FOUND));
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
       User user = userRepository.findById(id).orElseThrow(() -> new GenericServiceException("This user is not found with id: " + id, ErrorType.USER_NOT_FOUND));
       user.setPassword(newPassword);
       userRepository.save(user);
       return userMapper.toUserPublicDTO(user);
   }


}
