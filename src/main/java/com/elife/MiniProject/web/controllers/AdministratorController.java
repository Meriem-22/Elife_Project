package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.AdministratorService;
import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.exceptions.DuplicateUserException;
import com.elife.MiniProject.web.dto.AdministratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Administrator> registerAdministrator(@RequestBody AdministratorDTO administratorDTO) {
        try {
            Administrator administrator = administratorService.register(administratorDTO);
            return ResponseEntity.ok(administrator);
        } catch (DuplicateUserException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Long id) {
        Administrator administrator = administratorService.findById(id);
        return administrator != null ? ResponseEntity.ok(administrator) : ResponseEntity.notFound().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Administrator> getAdministratorByUsername(@PathVariable String username) {
        Administrator administrator = administratorService.findByUsername(username);
        return administrator != null ? ResponseEntity.ok(administrator) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(@RequestBody AdministratorDTO administratorDTO, @PathVariable Long id) {
        Administrator updatedAdministrator = administratorService.update(administratorDTO, id);
        return updatedAdministrator != null ? ResponseEntity.ok(updatedAdministrator) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        administratorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = administratorService.uploadPhoto(file, id);
            return ResponseEntity.ok(fileName);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/updatePhoto")
    public ResponseEntity<String> updatePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            administratorService.updatePhoto(id, file);
            return ResponseEntity.ok("Photo updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
