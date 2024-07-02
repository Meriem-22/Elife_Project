package com.elife.MiniProject.dao.repositories;

import com.elife.MiniProject.dao.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByUsername(String username);
    boolean existsByEmail(String email);

}
