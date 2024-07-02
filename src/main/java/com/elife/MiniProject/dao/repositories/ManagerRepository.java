package com.elife.MiniProject.dao.repositories;

import com.elife.MiniProject.dao.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByUserName(String userName);
    boolean existsByEmail(String email);
}
