package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.ManagerService;
import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.web.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long id) {
        Manager manager = managerService.getManagerById(id);
        if (manager != null) {
            return ResponseEntity.ok(ManagerDTO.convertToDTO(manager));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ManagerDTO>> getAllManagers() {
        List<ManagerDTO> managers = managerService.getAllManagers().stream()
                .map(ManagerDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(managers);
    }

    @PostMapping("/save")
    public ResponseEntity<ManagerDTO> saveManager(@RequestBody ManagerDTO managerDTO) {
        Manager manager = ManagerDTO.convertToEntity(managerDTO);
        Manager savedManager = managerService.saveManager(manager);
        return ResponseEntity.ok(ManagerDTO.convertToDTO(savedManager));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ManagerDTO> updateManager(@PathVariable Long id, @RequestBody ManagerDTO managerDTO) {
        Manager manager = ManagerDTO.convertToEntity(managerDTO);
        Manager updatedManager = managerService.updateManager(id, manager);
        if (updatedManager != null) {
            return ResponseEntity.ok(ManagerDTO.convertToDTO(updatedManager));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
