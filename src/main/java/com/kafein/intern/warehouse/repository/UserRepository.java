package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    User findById(int id);

}
