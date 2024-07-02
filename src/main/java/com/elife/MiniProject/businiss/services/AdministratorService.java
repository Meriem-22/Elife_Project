package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.AdministratorDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AdministratorService {

    Administrator register(AdministratorDTO administratorDTO) throws DuplicateUserException;

    Administrator findById(Long id);

    Administrator findByUsername(String username);

    Administrator update(AdministratorDTO administratorDTO, Long id);

    void delete(Long id);

    String uploadPhoto(MultipartFile file, Long id);

    void updatePhoto(Long id, MultipartFile file);

}


