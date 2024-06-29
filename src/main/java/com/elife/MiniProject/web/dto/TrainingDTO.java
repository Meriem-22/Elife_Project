package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Category;
import com.elife.MiniProject.dao.entities.Training;
import com.elife.MiniProject.dao.enums.TrainingStatus;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDTO {
    private Long id;
    private String name;
    private String description;
    private TrainingStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long categoryId;

    public static TrainingDTO convertToDTO(Training training) {
        return TrainingDTO.builder()
                .id(training.getId())
                .name(training.getName())
                .description(training.getDescription())
                .status(training.getStatus())
                .startDate(training.getStartDate())
                .endDate(training.getEndDate())
                .categoryId(training.getCategory().getId())
                .build();
    }

    public Training convertToEntity(Category category) {
        Training training = new Training();
        training.setId(this.id);
        training.setName(this.name);
        training.setDescription(this.description);
        training.setStatus(this.status);
        training.setStartDate(this.startDate);
        training.setEndDate(this.endDate);
        training.setCategory(category);
        return training;
    }
}
