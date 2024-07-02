package com.elife.MiniProject.dao.repositories;

import com.elife.MiniProject.dao.entities.Collaborator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
    Collaborator findByUsername(String username);
    boolean existsByEmail(String email);
}
