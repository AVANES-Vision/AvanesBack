package com.avanes.backend.repository;

import com.avanes.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    Page<User> findAllByUserIdIsNotNull(Pageable pageable);
    boolean existsByUsername(String username);
    Page<User> findAllByRoleEqualsIgnoreCase(String role, Pageable pageable);
}