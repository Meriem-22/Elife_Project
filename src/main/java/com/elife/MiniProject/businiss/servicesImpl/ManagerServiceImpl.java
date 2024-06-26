package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.ManagerService;
import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.dao.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Manager getManagerById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    @Override
    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager updateManager(Long id, Manager manager) {
        Optional<Manager> existingManager = managerRepository.findById(id);
        if (existingManager.isPresent()) {
            Manager updatedManager = existingManager.get();
            updatedManager.setUsername(manager.getUsername());
            updatedManager.setFirstName(manager.getFirstName());
            updatedManager.setLastName(manager.getLastName());
            updatedManager.setEmail(manager.getEmail());
            updatedManager.setPhoto(manager.getPhoto());
            updatedManager.setTeam(manager.getTeam());
            updatedManager.setTeam(manager.getTeam());
            return managerRepository.save(updatedManager);
        } else {
            return null; 
        }
    }

    @Override
    public void deleteManager(Long id) {
        managerRepository.deleteById(id);
    }
}
