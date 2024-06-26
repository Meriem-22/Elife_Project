package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Manager;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String role;
    private String team;

    public static ManagerDTO convertToDTO(Manager manager) {
        return ManagerDTO.builder()
                .id(manager.getId())
                .username(manager.getUsername())
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .email(manager.getEmail())
                .photo(manager.getPhoto())
                .role(manager.getRole().name())
                .team(manager.getTeam())
                .build();
    }

    public static Manager convertToEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setUsername(managerDTO.getUsername());
        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setEmail(managerDTO.getEmail());
        manager.setPhoto(managerDTO.getPhoto());
        manager.setTeam(managerDTO.getTeam());
        return manager;
    }
}
