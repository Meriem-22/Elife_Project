package com.elife.MiniProject.dao.entities;

import com.elife.MiniProject.dao.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "administrators")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Administrator extends User {

    @Column
    private int adminLevel;

    public Administrator(Long id, String userName, String password, String firstName, String lastName, String email,
            String photo, Role role, int adminLevel) {
        super(id, userName, password, firstName, lastName, email, photo, role);
        this.adminLevel = adminLevel;
    }
}

