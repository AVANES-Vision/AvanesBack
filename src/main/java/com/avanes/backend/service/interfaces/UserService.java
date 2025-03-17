package com.avanes.backend.service.interfaces;
import com.avanes.backend.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Integer userId);
    User getUserById(Integer userId);
    List<User> getAllUsers();
}