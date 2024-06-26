package com.elife.MiniProject.dao.entities;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request extends User {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private String status;

    
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String requestType;

    @Column(nullable = false)
    private LocalDate submissionDate;

    @Column
    private LocalDate resolutionDate;

    @Column(nullable = false)
    private String priority;

    @Column(length = 1000)
    private String comments;
}