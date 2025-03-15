package com.hapangama.avanesback.repository;

import com.hapangama.avanesback.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}