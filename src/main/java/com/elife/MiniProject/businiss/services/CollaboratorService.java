package com.elife.MiniProject.businiss.services;

import com.elife.MiniProject.dao.entities.Collaborator;

import java.util.List;

public interface CollaboratorService {
    Collaborator getCollaboratorById(Long id);
    List<Collaborator> getAllCollaborators();
    Collaborator saveCollaborator(Collaborator collaborator);
    Collaborator updateCollaborator(Long id, Collaborator collaborator);
    void deleteCollaborator(Long id);
}
