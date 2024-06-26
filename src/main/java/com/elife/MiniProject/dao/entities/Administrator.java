package com.elife.MiniProject.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrators")
public class Administrator extends User {
    @Column
    private int adminLevel;
}
