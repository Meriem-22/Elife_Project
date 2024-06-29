package com.elife.MiniProject.dao.repositories;

import com.elife.MiniProject.dao.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
