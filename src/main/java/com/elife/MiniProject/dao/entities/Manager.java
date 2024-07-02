package com.elife.MiniProject.dao.entities;

import com.elife.MiniProject.dao.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "managers")
public class Manager extends User {
    @Column
    private String team;

    
}
