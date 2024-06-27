package com.elife.MiniProject.businiss.servicesImpl;

import com.elife.MiniProject.businiss.services.CollaboratorService;
import com.elife.MiniProject.dao.entities.Collaborator;
import com.elife.MiniProject.dao.repositories.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @Override
    public Collaborator getCollaboratorById(Long id) {
        return collaboratorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Collaborator> getAllCollaborators() {
        return collaboratorRepository.findAll();
    }

    @Override
    public Collaborator saveCollaborator(Collaborator collaborator) {
        return collaboratorRepository.save(collaborator);
    }

    @Override
    public Collaborator updateCollaborator(Long id, Collaborator collaborator) {
        Optional<Collaborator> existingCollaborator = collaboratorRepository.findById(id);
        if (existingCollaborator.isPresent()) {
            Collaborator updatedCollaborator = existingCollaborator.get();
            updatedCollaborator.setUsername(collaborator.getUsername());
            updatedCollaborator.setFirstName(collaborator.getFirstName());
            updatedCollaborator.setLastName(collaborator.getLastName());
            updatedCollaborator.setEmail(collaborator.getEmail());
            updatedCollaborator.setPhoto(collaborator.getPhoto());
            updatedCollaborator.setDepartment(collaborator.getDepartment());
            // Update other properties as needed
            return collaboratorRepository.save(updatedCollaborator);
        } else {
            return null; // Or throw an exception if preferred
        }
    }

    @Override
    public void deleteCollaborator(Long id) {
        collaboratorRepository.deleteById(id);
    }
}
