package com.hapangama.avanesback.service.interfaces;
import com.hapangama.avanesback.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(Integer userId);
    User getUserById(Integer userId);
    List<User> getAllUsers();
}