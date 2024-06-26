package com.elife.MiniProject.businiss.services;
import com.elife.MiniProject.dao.entities.User;
import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<User> getAllUsers();
    User saveUser(User user);
    void deleteUser(Long id);
}
