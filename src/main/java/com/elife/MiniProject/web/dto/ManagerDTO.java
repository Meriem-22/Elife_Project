package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Manager;
import com.elife.MiniProject.dao.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String photo;
    private String role;
    private String team;

    public static ManagerDTO convertToDTO(Manager manager) {
        return ManagerDTO.builder()
                .id(manager.getId())
                .firstName(manager.getFirstName())
                .lastName(manager.getLastName())
                .email(manager.getEmail())
                .username(manager.getUsername())
                .photo(manager.getPhoto())
                .role(manager.getRole() != null ? manager.getRole().name() : null)
                .team(manager.getTeam())
                .build();
    }

    public static Manager convertToEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        manager.setId(managerDTO.getId());
        manager.setFirstName(managerDTO.getFirstName());
        manager.setLastName(managerDTO.getLastName());
        manager.setEmail(managerDTO.getEmail());
        manager.setUserName(managerDTO.getUsername());
        manager.setPhoto(managerDTO.getPhoto());
        manager.setRole(managerDTO.getRole() != null ? Role.valueOf(managerDTO.getRole()) : null);
        manager.setTeam(managerDTO.getTeam());
        return manager;
    }
}

