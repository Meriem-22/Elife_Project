package com.elife.MiniProject.businiss.services;
import org.springframework.web.multipart.MultipartFile;

import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.ManagerDTO;
import java.util.List;

public interface ManagerService {
    Manager register(ManagerDTO managerDTO) throws DuplicateUserException;
    Manager findByUsername(String username);
    List<Manager> getAllManagers();
    Manager update(ManagerDTO managerDTO, Long id);
    void delete(Long id);
    String uploadPhoto(MultipartFile file, Long id);
    void updatePhoto(Long id, MultipartFile file);
}
