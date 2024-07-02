package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.CollaboratorService;
import com.elife.MiniProject.dao.entities.Collaborator;
import com.elife.MiniProject.dao.enums.Role;
import com.elife.MiniProject.dao.repositories.CollaboratorRepository;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.CollaboratorDTO;
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
public class CollaboratorServiceImpl implements CollaboratorService {

    private final CollaboratorRepository collaboratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository, PasswordEncoder passwordEncoder) {
        this.collaboratorRepository = collaboratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collaborator register(CollaboratorDTO collaboratorDTO) throws DuplicateUserException {
        if (collaboratorRepository.existsByEmail(collaboratorDTO.getEmail())) {
            throw new DuplicateUserException("User already exists with email: " + collaboratorDTO.getEmail());
        }

        Role role;
        try {
            role = Role.valueOf(collaboratorDTO.getRole());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role: " + collaboratorDTO.getRole());
        }

        Collaborator collaborator = new Collaborator();
        collaborator.setUserName(collaboratorDTO.getUserName());
        collaborator.setPassword(passwordEncoder.encode(collaboratorDTO.getPassword()));
        collaborator.setFirstName(collaboratorDTO.getFirstName());
        collaborator.setLastName(collaboratorDTO.getLastName());
        collaborator.setEmail(collaboratorDTO.getEmail());
        collaborator.setRole(role);
        collaborator.setDepartment(collaboratorDTO.getDepartment());

        return collaboratorRepository.save(collaborator);
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorRepository.findById(id).orElse(null);
    }

    @Override
    public Collaborator findByUsername(String username) {
        return collaboratorRepository.findByUserName(username);
    }

    @Override
    public Collaborator update(CollaboratorDTO collaboratorDTO, Long id) {
        Collaborator existingCollaborator = collaboratorRepository.findById(id).orElse(null);
        if (existingCollaborator == null) {
            return null;
        }

        existingCollaborator.setFirstName(collaboratorDTO.getFirstName());
        existingCollaborator.setLastName(collaboratorDTO.getLastName());
        existingCollaborator.setEmail(collaboratorDTO.getEmail());
        existingCollaborator.setPhoto(collaboratorDTO.getPhoto());
        existingCollaborator.setRole(Role.valueOf(collaboratorDTO.getRole()));
        existingCollaborator.setDepartment(collaboratorDTO.getDepartment());

        return collaboratorRepository.save(existingCollaborator);
    }

    @Override
    public void delete(Long id) {
        deletePhoto(id);
        collaboratorRepository.deleteById(id);
    }

    @Override
    public List<Collaborator> getAllCollaborators() {
        return collaboratorRepository.findAll();
    }

    @Override
    public String uploadPhoto(MultipartFile file, Long id) {
        try {
            Collaborator collaborator = collaboratorRepository.findById(id).orElse(null);
            if (collaborator == null) {
                throw new IllegalArgumentException("Collaborator not found with id: " + id);
            }

            if (!file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String uploadDir = "./src/main/resources/static/photos/collaborators/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = id + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            collaborator.setPhoto(fileName);
            collaboratorRepository.save(collaborator);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not save file: " + ex.getMessage());
        }
    }

    @Override
public void updatePhoto(Long id, MultipartFile file) {
    try {
        Collaborator collaborator = collaboratorRepository.findById(id).orElse(null);
        if (collaborator == null) {
            throw new IllegalArgumentException("Collaborator not found with id: " + id);
        }

        deletePhoto(id);

        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        String uploadDir = "./src/main/resources/static/photos/collaborators/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        String fileName = id + "_" + originalFileName;
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        collaborator.setPhoto(fileName);
        collaboratorRepository.save(collaborator);
    } catch (IOException ex) {
        throw new RuntimeException("Could not update photo: " + ex.getMessage());
    }
}



    public void deletePhoto(Long id) {
        Collaborator collaborator = collaboratorRepository.findById(id).orElse(null);
        if (collaborator != null) {
            String photoFileName = collaborator.getPhoto();
            if (photoFileName != null && !photoFileName.isEmpty()) {
                String uploadDir = "./src/main/resources/static/photos/collaborators/";
                Path photoPath = Paths.get(uploadDir, photoFileName);

                try {
                    Files.deleteIfExists(photoPath);
                } catch (IOException ex) {
                    throw new RuntimeException("Error deleting file: " + ex.getMessage());
                }

                collaborator.setPhoto(null);
                collaboratorRepository.save(collaborator);
            }
        }
    }
}

