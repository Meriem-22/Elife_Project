package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.User;
import com.elife.MiniProject.web.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    List<User> getAllManagers();

    List<User> getAllCollaborators();

    List<User> getAllAdministrators();

    Optional<User> findByEmail(String email);

    User update(UserDTO userDTO, Long id);

    void delete(Long id);

    String uploadPhoto(MultipartFile file, Long id);

    void updatePhoto(Long id, MultipartFile file);
}
