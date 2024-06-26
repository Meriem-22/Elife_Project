package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Manager;

import java.util.List;

public interface ManagerService {
    Manager getManagerById(Long id);
    List<Manager> getAllManagers();
    Manager saveManager(Manager manager);
    Manager updateManager(Long id, Manager manager);
    void deleteManager(Long id);
}
