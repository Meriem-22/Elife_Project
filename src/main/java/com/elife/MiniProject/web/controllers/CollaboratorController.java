package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.CollaboratorService;
import com.elife.MiniProject.dao.entities.Collaborator;
import com.elife.MiniProject.web.dto.CollaboratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorService collaboratorService;

    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> getCollaboratorById(@PathVariable Long id) {
        Collaborator collaborator = collaboratorService.getCollaboratorById(id);
        if (collaborator != null) {
            return ResponseEntity.ok(CollaboratorDTO.convertToDTO(collaborator));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CollaboratorDTO>> getAllCollaborators() {
        List<CollaboratorDTO> collaborators = collaboratorService.getAllCollaborators().stream()
                .map(CollaboratorDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(collaborators);
    }

    @PostMapping("/save")
    public ResponseEntity<CollaboratorDTO> saveCollaborator(@RequestBody CollaboratorDTO collaboratorDTO) {
        Collaborator collaborator = CollaboratorDTO.convertToEntity(collaboratorDTO);
        Collaborator savedCollaborator = collaboratorService.saveCollaborator(collaborator);
        return ResponseEntity.ok(CollaboratorDTO.convertToDTO(savedCollaborator));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CollaboratorDTO> updateCollaborator(@PathVariable Long id, @RequestBody CollaboratorDTO collaboratorDTO) {
        Collaborator collaborator = CollaboratorDTO.convertToEntity(collaboratorDTO);
        Collaborator updatedCollaborator = collaboratorService.updateCollaborator(id, collaborator);
        if (updatedCollaborator != null) {
            return ResponseEntity.ok(CollaboratorDTO.convertToDTO(updatedCollaborator));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable Long id) {
        collaboratorService.deleteCollaborator(id);
        return ResponseEntity.noContent().build();
    }
}
