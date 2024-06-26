package com.elife.MiniProject.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elife.MiniProject.dao.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Ajoutez ici des méthodes personnalisées si nécessaire
}