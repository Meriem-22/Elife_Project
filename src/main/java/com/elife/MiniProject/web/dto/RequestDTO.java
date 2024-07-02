package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Request;
import com.elife.MiniProject.dao.enums.RequestStatus;
import com.elife.MiniProject.businiss.services.CollaboratorService;
import com.elife.MiniProject.businiss.services.TrainingService;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private Long id;
    private Long collaboratorId;
    private Long trainingId;
    private RequestStatus status;
    private LocalDate submissionDate;
    private LocalDate resolutionDate;
    private String comments;

    public static RequestDTO convertToDTO(Request request) {
        return RequestDTO.builder()
                .id(request.getId())
                .collaboratorId(request.getCollaborator().getId()) 
                .trainingId(request.getTraining().getId())
                .status(request.getStatus())
                .submissionDate(request.getSubmissionDate())
                .resolutionDate(request.getResolutionDate())
                .comments(request.getComments())
                .build();
    }

    public Request convertToEntity(CollaboratorService collaboratorService, TrainingService trainingService) {
        Request request = new Request();
        request.setId(this.id);
        request.setCollaborator(collaboratorService.findById(this.collaboratorId)); 
        request.setTraining(trainingService.getTrainingById(this.trainingId));
        request.setStatus(this.status);
        request.setSubmissionDate(this.submissionDate);
        request.setResolutionDate(this.resolutionDate);
        request.setComments(this.comments);
        return request;
    }
}

