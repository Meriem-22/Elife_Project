package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.ManagerService;
import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.web.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Manager> registerManager(@RequestBody ManagerDTO managerDTO) {
        try {
            Manager manager = managerService.register(managerDTO);
            return ResponseEntity.ok(manager);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

  

    @GetMapping("/username/{username}")
    public ResponseEntity<Manager> getManagerByUsername(@PathVariable String username) {
        Manager manager = managerService.findByUsername(username);
        if (manager != null) {
            return ResponseEntity.ok(manager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manager> updateManager(@RequestBody ManagerDTO managerDTO, @PathVariable Long id) {
        Manager updatedManager = managerService.update(managerDTO, id);
        if (updatedManager != null) {
            return ResponseEntity.ok(updatedManager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        managerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<String> uploadManagerPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = managerService.uploadPhoto(file, id);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/updatePhoto")
    public ResponseEntity<Void> updateManagerPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            managerService.updatePhoto(id, file);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Manager>> getAllManagers() {
        List<Manager> managers = managerService.getAllManagers();
        return ResponseEntity.ok(managers);
    }
}
