package com.elife.MiniProject.web.dto;

import com.elife.MiniProject.dao.entities.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String role;

    // Méthode statique pour convertir une entité User en DTO
    public static UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .role(user.getRole().name())
                .build();
    }

    // Méthode pour convertir ce DTO en entité User
    public User convertToEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPhoto(this.photo);
        // Assurez-vous de définir d'autres propriétés si nécessaire
        return user;
    }
}
