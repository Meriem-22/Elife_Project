package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.enums.TrainingStatus;

import lombok.Builder;

@Builder
public record TrainingStatusDTO(Long id, TrainingStatus status) {
    
    public static TrainingStatusDTO toTrainingStatusDTO(Long id, TrainingStatus status) {
        return TrainingStatusDTO.builder()
                .id(id)
                .status(status)
                .build();
    }
}

