package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.UserService;
import com.elife.MiniProject.dao.entities.User;
import com.elife.MiniProject.web.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = UserDTO.convertToDTO(userService.getUserById(id));
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers().stream()
                .map(UserDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.map(user -> ResponseEntity.ok(UserDTO.convertToDTO(user)))
                           .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','COLLABORATOR') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        User updatedUser = userService.update(userDTO, id);
        return ResponseEntity.ok(UserDTO.convertToDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN') and hasAuthority('DELETE_PRIVILEGE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/uploadPhoto")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = userService.uploadPhoto(file, id);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/updatePhoto")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<Void> updatePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            userService.updatePhoto(id, file);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all/managers")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<UserDTO>> getAllManagers() {
        List<UserDTO> userDTOs = userService.getAllManagers().stream()
                .map(UserDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/all/collaborators")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<UserDTO>> getAllCollaborators() {
        List<UserDTO> userDTOs = userService.getAllCollaborators().stream()
                .map(UserDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/all/administrators")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<UserDTO>> getAllAdministrators() {
        List<UserDTO> userDTOs = userService.getAllAdministrators().stream()
                .map(UserDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
