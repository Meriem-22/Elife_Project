package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.ManagerService;
import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.dao.enums.Role;
import com.elife.MiniProject.dao.repositories.ManagerRepository;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, PasswordEncoder passwordEncoder) {
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Manager register(ManagerDTO managerDTO) throws DuplicateUserException {
        if (managerRepository.existsByEmail(managerDTO.getEmail())) {
            throw new DuplicateUserException("User already exists with email: " + managerDTO.getEmail());
        }

        Role role;
        try {
            role = Role.valueOf(managerDTO.getRole());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + managerDTO.getRole());
        }

        Manager manager = new Manager();
        manager.setUserName(managerDTO.getUsername());
        manager.setPassword(passwordEncoder.encode(managerDTO.getPassword()));
        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setEmail(managerDTO.getEmail());
        manager.setRole(role);
        manager.setTeam(managerDTO.getTeam());

        return managerRepository.save(manager);
    }

    

    @Override
    public Manager findByUsername(String username) {
        return managerRepository.findByUserName(username);
    }

    @Override
    public List<Manager> getAllManagers() {
    return managerRepository.findAll();
    }

    @Override
    public Manager update(ManagerDTO managerDTO, Long id) {
        Manager existingManager = managerRepository.findById(id).orElse(null);
        if (existingManager == null) {
            return null;
        }

        existingManager.setFirstName(managerDTO.getFirstName());
        existingManager.setLastName(managerDTO.getLastName());
        existingManager.setEmail(managerDTO.getEmail());
        existingManager.setPhoto(managerDTO.getPhoto());
        existingManager.setRole(Role.valueOf(managerDTO.getRole()));
        existingManager.setTeam(managerDTO.getTeam());

        return managerRepository.save(existingManager);
    }

    @Override
    public void delete(Long id) {
        deletePhoto(id);
        managerRepository.deleteById(id);
    }

    @Override
    public String uploadPhoto(MultipartFile file, Long id) {
        try {
            Manager manager = managerRepository.findById(id).orElse(null);
            if (manager == null) {
                throw new IllegalArgumentException("Manager not found with id: " + id);
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/managers/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            manager.setPhoto(fileName);
            managerRepository.save(manager);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + ex.getMessage());
        }
    }

    @Override
    public void updatePhoto(Long id, MultipartFile file) {
        try {
            Manager manager = managerRepository.findById(id).orElse(null);
            if (manager == null) {
                throw new IllegalArgumentException("Manager not found with id: " + id);
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/managers/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            manager.setPhoto(fileName);
            managerRepository.save(manager);

        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + ex.getMessage());
        }
    }

    public void deletePhoto(Long id) {
        Manager manager = managerRepository.findById(id).orElse(null);
        if (manager != null) {
            String photoFileName = manager.getPhoto();
            if (photoFileName != null && !photoFileName.isEmpty()) {
                String uploadDir = "./src/main/resources/static/photos/managers/";
                Path photoPath = Paths.get(uploadDir, photoFileName);

                try {
                    Files.deleteIfExists(photoPath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file: " + ex.getMessage());
                }

                manager.setPhoto(null);
                managerRepository.save(manager);
            }
        }
    }

   
}
