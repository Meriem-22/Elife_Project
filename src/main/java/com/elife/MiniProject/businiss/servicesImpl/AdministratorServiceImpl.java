package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.AdministratorService;
import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.dao.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator getAdministratorById(Long id) {
        return administratorRepository.findById(id).orElse(null);
    }

    @Override
    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    @Override
    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
    }
}
