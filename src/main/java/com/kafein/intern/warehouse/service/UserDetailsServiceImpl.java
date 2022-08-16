package com.kafein.intern.warehouse.service;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.ErrorType;
import com.kafein.intern.warehouse.exception.GenericServiceException;
import com.kafein.intern.warehouse.repository.UserRepository;
import com.kafein.intern.warehouse.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadByUserId(int id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new GenericServiceException("----", ErrorType.INVALID_REQUEST);
        }
        return JwtUserDetails.create(user);
    }


}
