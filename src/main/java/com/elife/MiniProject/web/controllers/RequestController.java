package com.elife.MiniProject.web.controllers;

import com.elife.MiniProject.businiss.services.RequestService;
import com.elife.MiniProject.businiss.services.UserService;
import com.elife.MiniProject.businiss.services.TrainingService;
import com.elife.MiniProject.dao.entities.Request;
import com.elife.MiniProject.web.dto.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingService trainingService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER') and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<RequestDTO> getRequestById(@PathVariable Long id) {
        Request request = requestService.getRequestById(id);
        if (request != null) {
            return ResponseEntity.ok(RequestDTO.convertToDTO(request));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')and hasAuthority('READ_PRIVILEGE')")
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        List<RequestDTO> requests = requestService.getAllRequests().stream()
                .map(RequestDTO::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('COLLABORATOR')and hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO requestDTO) {
        Request request = requestDTO.convertToEntity(userService, trainingService);
        Request savedRequest = requestService.saveRequest(request);
        return ResponseEntity.ok(RequestDTO.convertToDTO(savedRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable Long id, @RequestBody RequestDTO requestDTO) {
        Request request = requestDTO.convertToEntity(userService, trainingService);
        Request updatedRequest = requestService.updateRequest(id, request);
        if (updatedRequest != null) {
            return ResponseEntity.ok(RequestDTO.convertToDTO(updatedRequest));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')and hasAuthority('DELETE_PRIVILEGE')")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/accept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long id) {
        requestService.acceptRequest(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refuse/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')and hasAuthority('UPDATE_PRIVILEGE')")
    public ResponseEntity<Void> refuseRequest(@PathVariable Long id) {
        requestService.refuseRequest(id);
        return ResponseEntity.ok().build();
    }
}


