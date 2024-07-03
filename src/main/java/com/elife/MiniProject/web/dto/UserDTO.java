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
    private String firstName;
    private String lastName;
    private String email;
    private String photo;
    private String role;

    public static UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .role(user.getRole().name())
                .build();
    }

    public User convertToEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFirstname(this.firstName);
        user.setLastname(this.lastName);
        user.setEmail(this.email);
        user.setPhoto(this.photo);

        return user;
    }
}
