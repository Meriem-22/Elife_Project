package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Request;
import com.elife.MiniProject.dao.enums.RequestStatus;
import com.elife.MiniProject.businiss.services.UserService;
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
    private Long userId;
    private Long trainingId;
    private RequestStatus status;
    private String description;
    private String requestType;
    private LocalDate submissionDate;
    private LocalDate resolutionDate;
    private String priority;
    private String comments;

    public static RequestDTO convertToDTO(Request request) {
        return RequestDTO.builder()
                .id(request.getId())
                .userId(request.getUser().getId())
                .trainingId(request.getTraining().getId())
                .status(request.getStatus())
                .submissionDate(request.getSubmissionDate())
                .resolutionDate(request.getResolutionDate())
                .comments(request.getComments())
                .build();
    }

    public Request convertToEntity(UserService userService, TrainingService trainingService) {
        Request request = new Request();
        request.setId(this.id);
        request.setUser(userService.getUserById(this.userId));
        request.setTraining(trainingService.getTrainingById(this.trainingId));
        request.setStatus(this.status);
        request.setSubmissionDate(this.submissionDate);
        request.setResolutionDate(this.resolutionDate);
        request.setComments(this.comments);
        return request;
    }
}
