package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.CollaboratorService;
import com.elife.MiniProject.dao.entities.Collaborator;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.CollaboratorDTO;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    private final CollaboratorService collaboratorService;

    @Autowired
    public CollaboratorController(CollaboratorService collaboratorService) {
        this.collaboratorService = collaboratorService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<CollaboratorDTO> getCollaboratorById(@PathVariable Long id) {
        try {
            Collaborator collaborator = collaboratorService.findById(id);
            CollaboratorDTO collaboratorDTO = CollaboratorDTO.convertToDTO(collaborator);
            return ResponseEntity.ok(collaboratorDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('COLLABORATOR') and hasAuthority('WRITE_PRIVILEGE')")

    public ResponseEntity<CollaboratorDTO> registerCollaborator(@RequestBody CollaboratorDTO collaboratorDTO) throws DuplicateUserException {
    Collaborator newCollaborator = collaboratorService.register(collaboratorDTO);
    CollaboratorDTO newCollaboratorDTO = CollaboratorDTO.convertToDTO(newCollaborator);
    return ResponseEntity.status(HttpStatus.CREATED).body(newCollaboratorDTO);
}


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COLLABORATOR')and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<CollaboratorDTO> updateCollaborator(@RequestBody CollaboratorDTO collaboratorDTO, @PathVariable Long id) {
        try {
            Collaborator updatedCollaborator = collaboratorService.update(collaboratorDTO, id);
            CollaboratorDTO updatedCollaboratorDTO = CollaboratorDTO.convertToDTO(updatedCollaborator);
            return ResponseEntity.ok(updatedCollaboratorDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('DELETE_PRIVILEGE')")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable Long id) {
        collaboratorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public List<Collaborator> getAllCollaborators() {
        return collaboratorService.getAllCollaborators();
    }
}

