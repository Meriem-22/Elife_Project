package com.elife.MiniProject.web.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.elife.MiniProject.dao.entities.User;
import com.elife.MiniProject.dao.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterUserDTO(
        @NotBlank(message = "firstname is required") String firstName,
        @NotBlank(message = "lastname is required") String lastName,
        @NotBlank(message = "email is required") @Email(message = "email format is not valid") String email,
        @NotBlank(message = "password is required")  @Size(min = 6, message = "Password must be at most 6 characters long") String password,
        @NotNull Role role) {
  
            public static User fromRegisterUserDTO(RegisterUserDTO registerUserDTO, PasswordEncoder passwordEncoder) {
        User user = User.builder()
                .firstName(registerUserDTO.firstName())
                .lastName(registerUserDTO.lastName())
                .email(registerUserDTO.email())
                .password(passwordEncoder.encode(registerUserDTO.password()))
                .role(registerUserDTO.role())
                .build();
        return user;
    }

    public static RegisterUserDTO toRegisterUserDTO(User user) {
        return new RegisterUserDTO(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole());
    }
}

