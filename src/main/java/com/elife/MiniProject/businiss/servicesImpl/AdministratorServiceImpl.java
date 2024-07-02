package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.AdministratorService;
import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.dao.enums.Role;
import com.elife.MiniProject.dao.repositories.AdministratorRepository;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.AdministratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Administrator register(AdministratorDTO administratorDTO) throws DuplicateUserException {
        if (administratorRepository.existsByEmail(administratorDTO.getEmail())) {
            throw new DuplicateUserException("User already exists with email: " + administratorDTO.getEmail());
        }

        Role role;
        try {
            role = Role.valueOf(administratorDTO.getRole());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + administratorDTO.getRole());
        }

        Administrator administrator = new Administrator(
            null,
            administratorDTO.getUsername(),
            passwordEncoder.encode(administratorDTO.getPassword()),
            administratorDTO.getFirstName(),
            administratorDTO.getLastName(),
            administratorDTO.getEmail(),
            administratorDTO.getPhoto(),
            role,
            administratorDTO.getAdminLevel()
        );

        return administratorRepository.save(administrator);
    }

    @Override
    public Administrator findById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    @Override
    public Administrator findByUsername(String username) {
        return administratorRepository.findByUserName(username);
    }

    @Override
    public Administrator update(AdministratorDTO administratorDTO, Long id) {
        Administrator existingAdministrator = administratorRepository.findById(id).orElse(null);
        if (existingAdministrator == null) {
            return null; 
        }

        existingAdministrator.setFirstName(administratorDTO.getFirstName());
        existingAdministrator.setLastName(administratorDTO.getLastName());
        existingAdministrator.setEmail(administratorDTO.getEmail());
        existingAdministrator.setPhoto(administratorDTO.getPhoto());
        existingAdministrator.setRole(Role.valueOf(administratorDTO.getRole()));
        existingAdministrator.setAdminLevel(administratorDTO.getAdminLevel());

        return administratorRepository.save(existingAdministrator);
    }

    @Override
    public void delete(Long id) {
        deletePhoto(id);
        administratorRepository.deleteById(id);
    }

    @Override
    public String uploadPhoto(MultipartFile file, Long id) {
        try {
            Administrator administrator = administratorRepository.findById(id).orElse(null);
            if (administrator == null) {
                throw new IllegalArgumentException("Administrator not found with id: " + id);
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/administrators/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();

            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            administrator.setPhoto(fileName);
            administratorRepository.save(administrator);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + ex.getMessage());
        }
    }

    @Override
    public void updatePhoto(Long id, MultipartFile file) {
        try {
            Administrator administrator = administratorRepository.findById(id).orElse(null);
            if (administrator == null) {
                throw new IllegalArgumentException("Administrator not found with id: " + id);
            }

            deletePhoto(id);

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/administrators/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();

            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            administrator.setPhoto(fileName);
            administratorRepository.save(administrator);
        } catch (IOException ex) {
            throw new RuntimeException("Could not update photo: " + ex.getMessage());
        }
    }

    public void deletePhoto(Long id) {
        Administrator administrator = administratorRepository.findById(id).orElse(null);
        if (administrator != null) {
            String photoFileName = administrator.getPhoto();
            if (photoFileName != null && !photoFileName.isEmpty()) {
                String uploadDir = "./src/main/resources/static/photos/administrators/";
                Path photoPath = Paths.get(uploadDir, photoFileName);

                try {
                    Files.deleteIfExists(photoPath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file: " + ex.getMessage());
                }

                administrator.setPhoto(null);
                administratorRepository.save(administrator);
            }
        }
    }
}
