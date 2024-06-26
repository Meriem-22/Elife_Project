package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Administrator;

public interface AdministratorService {
    Administrator getAdministratorById(Long id);
    Administrator saveAdministrator(Administrator administrator);
    void deleteAdministrator(Long id);
}
