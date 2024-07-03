package com.elife.MiniProject.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.elife.MiniProject.dao.enums.RequestStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING; 

    @Column(nullable = false)
    private LocalDate submissionDate;

    @Column
    private LocalDate resolutionDate;

    @Column(length = 1000)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "collaborator_id", nullable = false)
    private Collaborator collaborator;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;
}
