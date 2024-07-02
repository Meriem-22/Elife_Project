package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Collaborator;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.CollaboratorDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CollaboratorService {
    Collaborator register(CollaboratorDTO collaboratorDTO) throws DuplicateUserException;
    Collaborator findById(Long id);
    Collaborator findByUsername(String username);
    Collaborator update(CollaboratorDTO collaboratorDTO, Long id);
    void delete(Long id);
    List<Collaborator> getAllCollaborators();
    String uploadPhoto(MultipartFile file, Long id);
    void updatePhoto(Long id, MultipartFile file);
}
