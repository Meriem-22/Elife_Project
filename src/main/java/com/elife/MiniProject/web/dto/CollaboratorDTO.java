package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Collaborator;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String role;
    private String department;

    public static CollaboratorDTO convertToDTO(Collaborator collaborator) {
        return CollaboratorDTO.builder()
                .id(collaborator.getId())
                .username(collaborator.getUsername())
                .firstName(collaborator.getFirstName())
                .lastName(collaborator.getLastName())
                .email(collaborator.getEmail())
                .photo(collaborator.getPhoto())
                .role(collaborator.getRole().name())
                .department(collaborator.getDepartment())
                .build();
    }

    public static Collaborator convertToEntity(CollaboratorDTO collaboratorDTO) {
        Collaborator collaborator = new Collaborator();
        collaborator.setId(collaboratorDTO.getId());
        collaborator.setUsername(collaboratorDTO.getUsername());
        collaborator.setFirstName(collaboratorDTO.getFirstName());
        collaborator.setLastName(collaboratorDTO.getLastName());
        collaborator.setEmail(collaboratorDTO.getEmail());
        collaborator.setPhoto(collaboratorDTO.getPhoto());
        collaborator.setDepartment(collaboratorDTO.getDepartment());
        return collaborator;
    }
}
