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
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String photo;
    private String role;
    private int adminLevel;

    public static AdministratorDTO convertToDTO(Administrator administrator) {
        return AdministratorDTO.builder()
                .id(administrator.getId())
                .firstName(administrator.getFirstName())
                .lastName(administrator.getLastName())
                .email(administrator.getEmail())
                .email(administrator.getUsername())
                .photo(administrator.getPhoto())
                .role(administrator.getRole() != null ? administrator.getRole().name() : null)
                .adminLevel(administrator.getAdminLevel())
                .build();
    }

    public static Administrator convertToEntity(AdministratorDTO administratorDTO) {
        Administrator administrator = new Administrator();
        administrator.setId(administratorDTO.getId());
        administrator.setFirstName(administratorDTO.getFirstName());
        administrator.setLastName(administratorDTO.getLastName());
        administrator.setEmail(administratorDTO.getEmail());
        administrator.setEmail(administratorDTO.getUsername());
        administrator.setPhoto(administratorDTO.getPhoto());
        administrator.setRole(administratorDTO.getRole() != null ? Role.valueOf(administratorDTO.getRole()) : null);
        administrator.setAdminLevel(administratorDTO.getAdminLevel());
        return administrator;
    }
}
