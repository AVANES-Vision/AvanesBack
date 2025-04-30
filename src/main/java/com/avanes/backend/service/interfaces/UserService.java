package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Integer id);
    User getUserById(Integer id);
    Page<User> getAllUsers(String role, Pageable pageable);
    void importUsersFromCsv(MultipartFile file) throws IOException;
    byte[] exportUsersToCsv() throws IOException;
}