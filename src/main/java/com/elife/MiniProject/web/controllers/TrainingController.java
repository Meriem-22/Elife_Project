package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.CategoryService;
import com.elife.MiniProject.businiss.services.TrainingService;
import com.elife.MiniProject.dao.entities.Category;
import com.elife.MiniProject.dao.entities.Training;
import com.elife.MiniProject.web.dto.TrainingDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','COLLABORATOR')")
    public ResponseEntity<TrainingDTO> getTrainingById(@PathVariable Long id) {
        Training training = trainingService.getTrainingById(id);
        if (training != null) {
            return ResponseEntity.ok(TrainingDTO.convertToDTO(training));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','COLLABORATOR') and hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainings = trainingService.getAllTrainings().stream()
                .map(TrainingDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(trainings);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<TrainingDTO> saveTraining(@RequestBody TrainingDTO trainingDTO) {
        Category category = categoryService.getCategoryById(trainingDTO.getCategoryId());
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Training training = trainingDTO.convertToEntity(category);
        Training savedTraining = trainingService.saveTraining(training);
        return ResponseEntity.ok(TrainingDTO.convertToDTO(savedTraining));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<TrainingDTO> updateTraining(@PathVariable Long id, @RequestBody TrainingDTO trainingDTO) {
        Category category = categoryService.getCategoryById(trainingDTO.getCategoryId());
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Training training = trainingDTO.convertToEntity(category);
        Training updatedTraining = trainingService.updateTraining(id, training);
        if (updatedTraining != null) {
            return ResponseEntity.ok(TrainingDTO.convertToDTO(updatedTraining));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('DELETE_PRIVILEGE')")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<Void> activateTraining(@PathVariable Long id) {
        trainingService.activateTraining(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<Void> deactivateTraining(@PathVariable Long id) {
        trainingService.deactivateTraining(id);
        return ResponseEntity.ok().build();
    }


    
}
