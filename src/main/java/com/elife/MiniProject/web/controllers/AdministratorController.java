package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.AdministratorService;
import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.web.dto.AdministratorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable Long id) {
        Administrator administrator = administratorService.getAdministratorById(id);
        if (administrator != null) {
            return ResponseEntity.ok(AdministratorDTO.convertToDTO(administrator));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<AdministratorDTO> saveAdministrator(@RequestBody AdministratorDTO administratorDTO) {
        Administrator administratorToSave = AdministratorDTO.convertToEntity(administratorDTO);
        Administrator savedAdministrator = administratorService.saveAdministrator(administratorToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(AdministratorDTO.convertToDTO(savedAdministrator));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
        return ResponseEntity.noContent().build();
    }
}
