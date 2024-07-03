package com.elife.MiniProject.businiss.servicesImpl;


import com.elife.MiniProject.businiss.services.UserService;
import com.elife.MiniProject.dao.entities.User;
import com.elife.MiniProject.dao.enums.Role;
import com.elife.MiniProject.dao.repositories.UserRepository;
import com.elife.MiniProject.web.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

  

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User update(UserDTO userDTO, Long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstname(userDTO.getFirstName());
        existingUser.setLastname(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhoto(userDTO.getPhoto());
        existingUser.setRole(Role.valueOf(userDTO.getRole()));

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        deletePhoto(id);
        userRepository.deleteById(id);
    }

    
    @Override
    public String uploadPhoto(MultipartFile file, Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                throw new IllegalArgumentException("user not found with id: " + id);
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/users/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            user.setPhoto(fileName);
            userRepository.save(user);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + ex.getMessage());
        }
    }

    @Override
    public void updatePhoto(Long id, MultipartFile file) {
    try {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("user not found with id: " + id);
        }

        deletePhoto(id);

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        String uploadDir = "./src/main/resources/static/photos/users/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        String fileName = id + "_" + originalFileName;
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        user.setPhoto(fileName);
        userRepository.save(user);
    } catch (IOException ex) {
        throw new RuntimeException("Could not update photo: " + ex.getMessage());
    }
}



    public void deletePhoto(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            String photoFileName = user.getPhoto();
            if (photoFileName != null && !photoFileName.isEmpty()) {
                String uploadDir = "./src/main/resources/static/photos/users/";
                Path photoPath = Paths.get(uploadDir, photoFileName);

                try {
                    Files.deleteIfExists(photoPath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file: " + ex.getMessage());
                }

                user.setPhoto(null);
                userRepository.save(user);
            }
        }
    }

    @Override
    public List<User> getAllCollaborators() {
        return userRepository.findByRole(Role.COLLABORATOR);
    }

    @Override
    public List<User> getAllAdministrators() {
        return userRepository.findByRole(Role.ADMIN);
    }

    @Override
    public List<User> getAllManagers() {
        return userRepository.findByRole(Role.MANAGER);
    }

    
}
