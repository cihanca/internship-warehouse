package com.kafein.intern.warehouse.repository;

import com.kafein.intern.warehouse.entity.User;
import com.kafein.intern.warehouse.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> findAllByOrderByIdAsc();

    List<User> findByRoleOrderByIdAsc(Role role);

    List<User> findByRoleOrderByIdDesc(Role ROLE);

    User findByIdAndStatus(int id, Boolean status);

    List<User> findByStatusOrderByIdAsc(Boolean b);

    User findByEmailAndStatus(String email, Boolean b);
}
