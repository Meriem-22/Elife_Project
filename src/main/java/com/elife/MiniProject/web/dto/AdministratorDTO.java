package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.Administrator;
import com.elife.MiniProject.dao.enums.Role;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String role;
    private int adminLevel;

    public static AdministratorDTO convertToDTO(Administrator administrator) {
        return AdministratorDTO.builder()
                .id(administrator.getId())
                .username(administrator.getUsername())
                .firstName(administrator.getFirstName())
                .lastName(administrator.getLastName())
                .email(administrator.getEmail())
                .photo(administrator.getPhoto())
                .role(administrator.getRole().name())
                .adminLevel(administrator.getAdminLevel())
                .build();
    }

    public static Administrator convertToEntity(AdministratorDTO administratorDTO) {
        Administrator administrator = new Administrator();
        administrator.setId(administratorDTO.getId());
        administrator.setUsername(administratorDTO.getUsername());
        administrator.setFirstName(administratorDTO.getFirstName());
        administrator.setLastName(administratorDTO.getLastName());
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setPhoto(administratorDTO.getPhoto());
        administrator.setRole(administrator.getRole() != null ? Role.valueOf(administratorDTO.getRole()) : null);
        administrator.setAdminLevel(administratorDTO.getAdminLevel());
        return administrator;
    }
}


